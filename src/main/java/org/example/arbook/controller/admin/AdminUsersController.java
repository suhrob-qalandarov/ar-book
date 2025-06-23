package org.example.arbook.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.entity.User;
import org.example.arbook.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.arbook.util.ApiConstants.*;
import static org.example.arbook.util.ExceptionCodes.CODE_200;

@Tag(name = "Admin Users Controller", description = "API for managing users in the admin panel")
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(API + V1 + ADMIN + USERS)
public class AdminUsersController {

    private final UserRepository userRepository;

    @Operation(summary = "Get all users list", responses = {
            @ApiResponse(responseCode = CODE_200, description = "Users list retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<User>> getCategories(){
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
