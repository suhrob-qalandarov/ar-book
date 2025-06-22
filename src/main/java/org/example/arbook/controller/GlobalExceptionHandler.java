package org.example.arbook.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.arbook.exception.AttachmentNotFoundException;
import org.example.arbook.exception.BookNotFoundException;
import org.example.arbook.exception.CategoryNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.validation.ConstraintViolationException; // Make sure this is the one you're using

import org.springframework.http.HttpMethod;
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
import java.util.NoSuchElementException;

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
     * Handles cases where a category is not found.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("category", ex.getMessage());
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles business logic errors thrown by services, including category already exists.
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
            case "SMS code cannot be empty" -> fieldErrors.put("code", "SMS code cannot be empty");
            case "SMS code must be a 6 digit number" -> fieldErrors.put("code", "SMS code must be a 6 digit number");
            case "Invalid phone number or password" -> fieldErrors.put("credentials", "Invalid phone number or password");
            default -> {
                if (message.startsWith("Category already exists with name:")) {
                    fieldErrors.put("name", message);
                } else {
                    fieldErrors.put("general", message);
                }
            }
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
    public ResponseEntity<Map<String, Object>> handleGenericException(HttpServletRequest request, Exception ex) {
        // If it's a CORS preflight request — allow it
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return ResponseEntity.ok()
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS")
                    .header("Access-Control-Allow-Headers", "*")
                    .build();
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("error", "Internal Server Error");
        response.put("message", "An unexpected error occurred");
        response.put("timestamp", LocalDateTime.now().format(formatter));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles custom exception when a category is not found.
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("categoryId", ex.getMessage());
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles custom exception when an attachment is not found.
     */
    @ExceptionHandler(AttachmentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAttachmentNotFoundException(AttachmentNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("attachmentId", ex.getMessage());
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAttachmentNotFoundException(BookNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", "Not Found");
        response.put("message", ex.getMessage());
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        fieldErrors.put("bookId", ex.getMessage());
        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", "Validation failed");
        response.put("timestamp", LocalDateTime.now().format(formatter));

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString(); // e.g. "getOneBook.bookId"
            String message = violation.getMessage(); // e.g. "must be greater than 0"
            fieldErrors.put(field, message);
        });

        response.put("fieldErrors", fieldErrors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}