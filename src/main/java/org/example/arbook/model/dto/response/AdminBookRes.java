package org.example.arbook.model.dto.response;

import org.example.arbook.model.enums.BookStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record AdminBookRes(
        UUID id,
        String title,
        String description,
        Integer totalPages,
        Integer totalLanguages,
        BookStatus status,
        String  categoryName,
        UUID attachmentId,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}
