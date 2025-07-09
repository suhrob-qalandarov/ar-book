package org.example.arbook.model.dto.response;

public record UserPageContentRes(
        Long id,
        String text,
        Long audioId,
        Long languageId
) {
}
