package org.example.arbook.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.LoginReq;
import org.example.arbook.model.dto.request.RegisterReq;
import org.example.arbook.model.dto.request.VerifyPhoneReq;
import org.example.arbook.model.dto.response.LoginRes;
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
    public ResponseEntity<LoginRes> logIn(@Valid @RequestBody LoginReq loginReq) {
        LoginRes loginRes = authService.logIn(loginReq);
        return ResponseEntity.ok(loginRes);
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
