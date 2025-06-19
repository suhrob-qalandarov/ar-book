package org.example.arbook.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.LogInDTO;
import org.example.arbook.model.dto.request.RegisterReq;
import org.example.arbook.model.dto.request.VerifyPhoneReq;
import org.example.arbook.model.dto.response.LogInResDTO;
import org.example.arbook.service.interfaces.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.example.arbook.util.ApiConstants.*;

/**
 * Controller for handling authentication-related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(LOGIN)
    public ResponseEntity<LogInResDTO> logIn(@RequestBody LogInDTO logInDTO) {
        LogInResDTO logInResDTO = authService.logIn(logInDTO);
        return ResponseEntity.ok(logInResDTO);
    }

    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterReq registerReq) {
        authService.register(registerReq);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Successfully sent code"));
    }

    @PostMapping(VERIFY)
    public ResponseEntity<?> verifyPhoneNumber(@Valid @RequestBody VerifyPhoneReq verifyPhoneReq) {
        authService.verifyPhoneNumber(verifyPhoneReq.phoneNumber(), verifyPhoneReq.code());
        return ResponseEntity.ok(Map.of("message", "Phone number verified successfully"));
    }
}
