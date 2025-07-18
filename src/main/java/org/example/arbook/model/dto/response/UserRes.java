package org.example.arbook.model.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserRes(
        UUID id,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
