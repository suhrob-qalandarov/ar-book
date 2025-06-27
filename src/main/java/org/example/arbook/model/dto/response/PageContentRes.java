package org.example.arbook.model.dto.response;

import java.time.LocalDateTime;

public record PageContentRes(
        Long id,
        String text,
        Long audioId,
        Long languageId,


        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}
