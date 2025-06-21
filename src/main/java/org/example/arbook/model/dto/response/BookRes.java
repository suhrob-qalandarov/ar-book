package org.example.arbook.model.dto.response;

import org.example.arbook.model.enums.BookStatus;

public record BookRes(
        Long id,
        String description,
        Integer totalPages,
        Short totalLanguages,
        BookStatus status,
        String attachmentUrl
        ) {

}