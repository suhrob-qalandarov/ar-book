package org.example.arbook.model.dto.response;

import java.util.List;
import java.util.UUID;

public record UserBookRes(
        UUID id,
        String title,
        String description,
        Integer totalPages,
        Integer totalLanguages,
        String categoryName,
        String attachmentUrl,
        Boolean isActive,
        List<UserBookPageRes> bookPages
        ) {
}
