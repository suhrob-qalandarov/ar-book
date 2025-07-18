package org.example.arbook.model.dto.response.auth;

public record AuthResponse(
        String message,
        UserRes user
) {
}
