package org.example.arbook.model.dto.response;

import org.example.arbook.model.enums.BookStatus;

import java.util.UUID;

public record BookRes(
        UUID id,
        String title,
        String description,
        Integer totalPages,
        Integer totalLanguages,
        BookStatus status,
        String attachmentUrl
        ) {

}