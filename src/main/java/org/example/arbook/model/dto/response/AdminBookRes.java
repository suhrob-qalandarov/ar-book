package org.example.arbook.model.dto.response;

import org.example.arbook.model.enums.BookStatus;

import java.time.LocalDateTime;

public record AdminBookRes(
        Long id,
        String title,
        String description,
        Integer totalPages,
        Integer totalLanguages,
        BookStatus status,
        String  categoryName,
        Long attachmentId,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}
