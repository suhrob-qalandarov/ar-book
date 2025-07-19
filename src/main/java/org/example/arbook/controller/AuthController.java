package org.example.arbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.LoginReq;
import org.example.arbook.model.dto.request.RegisterReq;
import org.example.arbook.model.dto.request.VerifyPhoneReq;
import org.example.arbook.model.dto.request.auth.CodeVerificationReq;
import org.example.arbook.model.dto.request.auth.PhoneVerificationReq;
import org.example.arbook.model.dto.response.LoginRes;
import org.example.arbook.model.dto.response.auth.AuthResponse;
import org.example.arbook.service.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.example.arbook.util.ApiConstants.*;

/**
 * Controller for handling authentication-related endpoints.
 */
@RestController
@RequiredArgsConstructor

@RequestMapping(API + V1 + AUTH)
@Tag(name = "Authorization Controller", description = "Login//Logout/Register/Verify phone number endpoints for all actions accordingly")
public class AuthController {

    private final AuthService authService;


    @PostMapping(LOGIN)
    public ResponseEntity<String> sendLoginVerificationCode(@Valid @RequestBody PhoneVerificationReq phoneVerificationReq) {
        String message = authService.sendLoginVerificationCode(phoneVerificationReq);
        return ResponseEntity.ok(message);
    }

    @PostMapping(VERIFY)
    public ResponseEntity<AuthResponse> verifyBothRegisterAndLogin(
            @Valid @RequestBody CodeVerificationReq codeVerificationReq,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.verifyBothRegisterAndLogin(codeVerificationReq, response);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping(LOGOUT)
    @Operation(summary = "Logout", description = "Logs out the authenticated user by clearing the JWT cookie and invalidating the session.")
    public ResponseEntity<?> logOut(
            HttpServletResponse response
    ) {
        String message =authService.logOut(response);
        return ResponseEntity.ok(message);
    }








    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterReq registerReq) {
        authService.register(registerReq);
        return ResponseEntity.ok(Map.of("message", "Successfully sent code"));
    }

    @PostMapping(VERIFY)
    public ResponseEntity<?> verifyPhoneNumber(@Valid @RequestBody VerifyPhoneReq verifyPhoneReq) {
        authService.verifyPhoneNumber(verifyPhoneReq.phoneNumber(), verifyPhoneReq.code());
        return ResponseEntity.ok(Map.of("message", "Phone number verified successfully"));
    }


}
