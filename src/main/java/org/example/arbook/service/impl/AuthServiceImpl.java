package org.example.arbook.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.arbook.config.security.JwtService;
import org.example.arbook.model.dto.request.LogInDTO;
import org.example.arbook.model.dto.response.LogInResDTO;
import org.example.arbook.repository.UserRepository;
import org.example.arbook.service.interfaces.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Override
    public LogInResDTO logIn(LogInDTO logInDTO) {
        UsernamePasswordAuthenticationToken auth = null;
        try {
            auth = new UsernamePasswordAuthenticationToken(
                    logInDTO.getPhoneNumber(), logInDTO.getPassword()
            );
            authenticationManager.authenticate(auth);
            String token = jwtService.generateToken(logInDTO.getPhoneNumber());
            return new LogInResDTO(token, " Logged In successfully");
        } catch (DisabledException e) {
            return new LogInResDTO(null, "Account is not active");
        } catch (BadCredentialsException e) {
            return new LogInResDTO(null, "Invalid Phone number or Password");
        } catch (UsernameNotFoundException e) {
            return new LogInResDTO(null, "User not found");
        } catch (Exception e) {
            return new LogInResDTO(null, "Login failed");
        }


    }
}
