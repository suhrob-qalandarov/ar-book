package org.example.arbook.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.arbook.config.security.JwtService;
import org.example.arbook.model.dto.request.LoginReq;
import org.example.arbook.model.dto.request.RegisterReq;
import org.example.arbook.model.entity.Role;
import org.example.arbook.model.entity.User;
import org.example.arbook.repository.RoleRepository;
import org.example.arbook.repository.UserRepository;
import org.example.arbook.service.interfaces.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public String logIn(LoginReq loginReq) {
        // Verify user exists
        User user = userRepository.findByPhoneNumberOptional(loginReq.phoneNumber())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with phone number: " + loginReq.phoneNumber()));

        // Check if user is active
        if (!user.isEnabled()) {
            throw new DisabledException("User account is disabled");
        }

        try {
            // Perform authentication
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginReq.phoneNumber(), loginReq.password()
            );
            authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Invalid phone number or password");
        } catch (DisabledException e) {
            throw new DisabledException("User account is disabled");
        }

        // Generate JWT token
        return jwtService.generateToken(loginReq.phoneNumber());
    }

    /**
     * Registers a new user based on the provided request.
     * @param registerReq The registration request containing user details.
     * @throws IllegalArgumentException if validation fails.
     */
    @Transactional
    @Override
    public void register(RegisterReq registerReq) {

        if (!registerReq.password().equals(registerReq.confirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // Check for duplicate phone number
        if (userRepository.existsByPhoneNumber((registerReq.phoneNumber()))) {
            throw new IllegalArgumentException("Phone number already registered");
        }

        String verificationCode = generateVerificationCode();
        System.err.println("Generated SMS code: " + verificationCode);

        List<Role> roles = roleRepository.findAll();

        // Map DTO to entity
        User user = User.builder()
                .firstName(registerReq.firstName())
                .lastName(registerReq.lastName())
                .phoneNumber(registerReq.phoneNumber())
                .password(passwordEncoder.encode(registerReq.password()))
                .verificationCode(verificationCode)
                .roles(roles.stream().findFirst().stream().toList())
                .isActive(false)
                .build();

        // Save user
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void verifyPhoneNumber(String phoneNumber, String code) {
        User user = userRepository.findByPhoneNumberOptional(phoneNumber)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (user.getIsActive()) {
            throw new IllegalArgumentException("Phone number already verified");
        }
        if (!code.equals(user.getVerificationCode())) {
            throw new IllegalArgumentException("Invalid verification code");
        }
        user.setVerificationCode(null);
        user.setIsActive(true);
        userRepository.save(user);
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000); // 6-digit code
        return String.valueOf(code);
    }
}
