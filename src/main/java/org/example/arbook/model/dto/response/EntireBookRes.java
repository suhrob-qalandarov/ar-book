package org.example.arbook.model.dto.response;

import lombok.Builder;
import org.example.arbook.model.enums.BookStatus;

import java.util.List;
@Builder
public record EntireBookRes(

        Long bookId,
        String title,
        String description,
        Integer totalPages,
        Integer totalLanguages,
        BookStatus bookStatus,
        Long bookCoverId,

        List<BookPageRes> bookPages

) {
}
