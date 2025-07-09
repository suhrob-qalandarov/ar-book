package org.example.arbook.model.dto.response;

import java.util.List;

public record UserBookRes(
        Long id,
        String title,
        String description,
        Integer totalPages,
        Integer totalLanguages,
        String categoryName,
        Long attachmentId,
        Boolean isActive,
        List<UserBookPageRes> bookPages
        ) {
}
