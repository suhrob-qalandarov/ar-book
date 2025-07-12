package org.example.arbook.model.dto.response;

import lombok.Builder;

@Builder
public record UserRes(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber
) {
}
