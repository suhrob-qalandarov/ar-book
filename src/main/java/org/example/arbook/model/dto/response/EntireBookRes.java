package org.example.arbook.model.dto.response;

import lombok.Builder;
import org.example.arbook.model.enums.BookStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record EntireBookRes(

        UUID bookId,
        String title,
        String description,
        Integer totalPages,
        Integer totalLanguages,
        String categoryName,
        BookStatus bookStatus,
        UUID bookCoverId,

        List<BookPageRes> bookPages,


        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        Boolean isActive

) {
}
