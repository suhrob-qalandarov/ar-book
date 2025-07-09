package org.example.arbook.model.dto.response;

import java.util.List;
import java.util.UUID;

public record LoginRes(
        String token,
        List<UUID> qrCodeUUIDs,
        String message) {

}
