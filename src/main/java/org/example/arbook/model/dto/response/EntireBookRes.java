package org.example.arbook.model.dto.response;

import lombok.Builder;
import org.example.arbook.model.enums.BookStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record EntireBookRes(

        Long bookId,
        String title,
        String description,
        Integer totalPages,
        Integer totalLanguages,
        String categoryName,
        BookStatus bookStatus,
        Long bookCoverId,

        List<BookPageRes> bookPages,


        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy,
        Boolean isActive

) {
}
