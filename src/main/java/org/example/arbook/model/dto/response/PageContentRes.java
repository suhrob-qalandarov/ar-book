package org.example.arbook.model.dto.response;

public record PageContentRes(
        String text,
        Long audioId,
        Long languageId
) {
}
