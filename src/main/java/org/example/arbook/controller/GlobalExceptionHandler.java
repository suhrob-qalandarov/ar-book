package org.example.arbook.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for REST controllers, providing consistent error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    /**
     * Handles validation errors from DTOs annotated with @Valid.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", "Validation failed");
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles invalid credentials during authentication.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", "Unauthorized");
        response.put("message", "Invalid phone number or password");
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("credentials", "Invalid phone number or password");
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles disabled user accounts during authentication.
     */
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Map<String, Object>> handleDisabledException(DisabledException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("error", "Forbidden");
        response.put("message", "User account is disabled");
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("account", "User account is disabled");
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * Handles cases where the user is not found during authentication.
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", "User not found");
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("phoneNumber", "User not found");
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles business logic errors thrown by services.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", "Validation failed");
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        String message = ex.getMessage();
        switch (message) {
            case "Passwords do not match" -> fieldErrors.put("confirmPassword", "Passwords do not match");
            case "Phone number already registered" -> fieldErrors.put("phoneNumber", "Phone number already registered");
            case "User not found" -> fieldErrors.put("phoneNumber", "User not found");
            case "Invalid verification code" -> fieldErrors.put("code", "Invalid verification code");
            case "Phone number already verified" -> fieldErrors.put("phoneNumber", "Phone number already verified");
            case "Invalid phone number or password" ->
                    fieldErrors.put("credentials", "Invalid phone number or password");
            default -> fieldErrors.put("general", message);
        }
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles database constraint violations (e.g., unique constraints).
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.CONFLICT.value());
        response.put("error", "Conflict");
        response.put("message", "Database constraint violation");
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        if (ex.getMessage().contains("phone_number")) {
            fieldErrors.put("phoneNumber", "Phone number already registered");
        } else {
            fieldErrors.put("general", "Failed to save data due to database constraint");
        }
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    /**
     * Handles uncaught exceptions to prevent stack trace leaks.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", "An unexpected error occurred");
        response.put("timestamp", LocalDateTime.now().format(formatter));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
