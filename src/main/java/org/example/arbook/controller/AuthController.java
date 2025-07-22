package org.example.arbook.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.RegisterReq;
import org.example.arbook.model.dto.request.auth.CodeVerificationReq;
import org.example.arbook.model.dto.request.auth.PhoneVerificationReq;
import org.example.arbook.model.dto.response.LoginRes;
import org.example.arbook.model.dto.response.auth.UserRes;
import org.example.arbook.model.dto.response.auth.AuthResponse;
import org.example.arbook.model.entity.User;
import org.example.arbook.service.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/me")
    public ResponseEntity<UserRes> getUserData(@AuthenticationPrincipal User user) {
        UserRes userRes = authService.getUserData(user);
        return ResponseEntity.ok(userRes);
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> sendLoginVerificationCode(@Valid @RequestBody PhoneVerificationReq phoneVerificationReq) {
        String message = authService.sendLoginVerificationCode(phoneVerificationReq);
        return ResponseEntity.ok(message);
    }

    @PostMapping(REGISTER)
    public ResponseEntity<?> register(@Valid @RequestBody RegisterReq registerReq) {
        String code = authService.register(registerReq);
        return ResponseEntity.ok(code);
    }

    @PostMapping(VERIFY)
    public ResponseEntity<LoginRes> verifyBothRegisterAndLogin(
            @Valid @RequestBody CodeVerificationReq codeVerificationReq,
            HttpServletResponse response
    ) {
        LoginRes loginRes = authService.verifyBothRegisterAndLogin(codeVerificationReq, response);
        return ResponseEntity.ok(loginRes);
    }

    @PostMapping(LOGOUT)
    @Operation(summary = "Logout", description = "Logs out the authenticated user by clearing the JWT cookie and invalidating the session.")
    public ResponseEntity<?> logOut(
            HttpServletResponse response
    ) {
        String message =authService.logOut(response);
        return ResponseEntity.ok(message);
    }
}
