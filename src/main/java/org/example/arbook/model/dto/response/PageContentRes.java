package org.example.arbook.model.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record PageContentRes(
        UUID id,
        String text,
        UUID audioId,
        UUID languageId,


        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}
