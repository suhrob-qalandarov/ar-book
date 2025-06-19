package org.example.arbook.controller;

import org.example.arbook.model.dto.request.LogInDTO;
import org.example.arbook.model.dto.response.LogInResDTO;
import org.example.arbook.service.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.arbook.util.ApiConstants.*;

@RestController
@RequestMapping(API + V1 + AUTH)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LogInResDTO> logIn(@RequestBody LogInDTO logInDTO) {
        LogInResDTO logInResDTO = authService.logIn(logInDTO);
        return ResponseEntity.ok(logInResDTO);
    }

}
