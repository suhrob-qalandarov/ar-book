package org.example.arbook.model.dto.response.auth;

import java.util.List;
import java.util.UUID;

public record UserRes(
        UUID  id,
        String firstName,
        String lastName,
        String phoneNumber,
        List<String> roles,
        List<UUID> qrCodeUUIDs
) {
}
