package org.example.arbook.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.arbook.config.security.JwtService;
import org.example.arbook.model.dto.request.RegisterReq;
import org.example.arbook.model.dto.request.auth.CodeVerificationReq;
import org.example.arbook.model.dto.request.auth.PhoneVerificationReq;
import org.example.arbook.model.dto.response.LoginRes;
import org.example.arbook.model.dto.response.auth.UserRes;
import org.example.arbook.model.entity.QrCode;
import org.example.arbook.model.entity.Role;
import org.example.arbook.model.entity.User;
import org.example.arbook.model.enums.QrCodeStatus;
import org.example.arbook.repository.QrCodeRepository;
import org.example.arbook.repository.RoleRepository;
import org.example.arbook.repository.UserRepository;
import org.example.arbook.service.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${jwt.expiration}")
    private Integer expirationTimeInMills;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final QrCodeRepository qrCodeRepository;

    /**
     * Registers a new user based on the provided request.
     * @param registerReq The registration request containing user details.
     * @throws IllegalArgumentException if validation fails.
     */
    @Transactional
    @Override
    public String register(RegisterReq registerReq) {
        String verificationCode = generateVerificationCode();
        System.err.println("Generated SMS code: " + verificationCode);
        Optional<User> optionalUser = userRepository.findByPhoneNumberOptional(registerReq.phoneNumber());

        // Check for duplicate phone number
        if (optionalUser.isPresent()) {
            User dbUser = optionalUser.get();
            if (registerReq.phoneNumber().equals(dbUser.getPhoneNumber()) && dbUser.isEnabled()) {
                throw new IllegalArgumentException("Phone number already registered");

            } else if (dbUser.getPhoneNumber().equals(registerReq.phoneNumber())) {
                dbUser.setVerificationCode(verificationCode);
                return verificationCode;
            }
        }

        List<Role> roleUser = roleRepository.findALlByRoleNameIn(List.of("ROLE_USER"));

        // Map DTO to entity
        User user = User.builder()
                .firstName(registerReq.firstName())
                .lastName(registerReq.lastName())
                .phoneNumber(registerReq.phoneNumber())
                .verificationCode(verificationCode)
                .password("1")
                .roles(roleUser)
                .isActive(false)
                .build();

        // Save user
        User saved = userRepository.save(user);
        saved.setPassword(passwordEncoder.encode(saved.getId().toString()));

        return verificationCode;
    }

    /// 👇👇👇👇👇⬇️⬇️⬇️⬇️⬇️⬇️ Shu erdan Boshlab LOGIN & LOGOUT & VERIFY CODE lar yangilani yaratilgan !!
    @Transactional
    @Override
    public String sendLoginVerificationCode(PhoneVerificationReq phoneVerificationReq) {
        User userFromDB = findUserFromDB(phoneVerificationReq.phoneNumber());

        if (userFromDB.getVerificationCode() != null && !userFromDB.isEnabled()) {
            throw new DisabledException("Your account has not been verified yet . Please Sign Up again!");
        }
        else if (!userFromDB.isEnabled()) {
            throw new DisabledException("User account is disabled");
        }

        String verificationCode = generateVerificationCode();
        log.warn("Generated SMS code: " + verificationCode);

        userFromDB.setVerificationCode(verificationCode);
        return verificationCode;
    }

    @Transactional
    @Override
    public LoginRes verifyBothRegisterAndLogin(
            CodeVerificationReq codeVerificationReq,
            HttpServletResponse response
    ) {

        User user = findUserFromDB(codeVerificationReq.phoneNumber());

        validateVerificationCode(codeVerificationReq, user);

        authenticateUser(user);

        //generateTokenAndSetToCookie(user.getPhoneNumber(), response);

        String token = jwtService.generateToken(codeVerificationReq.phoneNumber());

        return new LoginRes(
                token,
                mapToUserResponse(user)
        );
    }

    @Override
    public String logOut(HttpServletResponse response) {
        // Clear the JWT cookie
        ResponseCookie cookie = ResponseCookie.from("ar-book-token", "")
                .httpOnly(true)
                .secure(false) // Match the setting used in generateTokenAndSetToCookie
                .path("/") // Match the path used in generateTokenAndSetToCookie
                .maxAge(0) // Expire the cookie immediately
                .sameSite("Lax") // Match the sameSite setting
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Optionally clear the SecurityContext
        SecurityContextHolder.clearContext();
        return "You have been logged out successfully.";
    }

    @Transactional
    @Override
    public UserRes getUserData(User user) {
        User dbUser = userRepository.findByPhoneNumber(user.getPhoneNumber());
        return mapToUserResponse(dbUser);
    }

    private void authenticateUser(User user) {
        try {
            // Perform authentication
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    user.getPhoneNumber(), user.getId()
            );
            authenticationManager.authenticate(authToken);

        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid phone number or password");
        } catch (DisabledException e) {
            throw new DisabledException("User account is disabled");
        }
    }

    private static void validateVerificationCode(CodeVerificationReq codeVerificationReq, User user) {
        if (user.getIsActive() && user.getVerificationCode() == null) {
            throw new IllegalArgumentException(""" 
                    No Verification code has been sent!
                    Please Login again !""");

        } else if (!user.getIsActive() && user.getVerificationCode() != null) {
            user.setIsActive(true);

        } else if (!codeVerificationReq.code().equals(user.getVerificationCode())) {
            throw new IllegalArgumentException("Invalid verification code");
        }
        user.setVerificationCode(null);
    }

    @Transactional
    public UserRes mapToUserResponse(User user) {
        List<UUID> qrCodeUUIDs = qrCodeRepository
                .findAllByIsActiveTrueAndStatusAndUserId(QrCodeStatus.ACTIVE, user.getId())
                .stream()
                .map(QrCode::getId)
                .toList();

        System.out.println(qrCodeUUIDs);

        return new UserRes(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getRoles().stream().map(role -> role.getRoleName().name()).toList(),
                qrCodeUUIDs
        );
    }

    private void generateTokenAndSetToCookie(String phoneNumber, HttpServletResponse response) {
        String token = jwtService.generateToken(phoneNumber);
        ResponseCookie cookie = ResponseCookie.from("ar-book-token", token)
                .httpOnly(true)
                .secure(false) // Only if using HTTPS
                .path("/") // Available across the app
                .maxAge(expirationTimeInMills / 1000) //Time given in seconds
                .sameSite("Lax")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private User findUserFromDB(String phoneNumber) {
        return userRepository.findByPhoneNumberOptional(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + phoneNumber));
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // 6-digit code
        return String.valueOf(code);
    }
}
