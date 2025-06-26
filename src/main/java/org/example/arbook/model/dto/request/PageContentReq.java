package org.example.arbook.model.dto.request;

public record PageContentReq(
        String title,
        Long audioId,
        Long languageId
) {
}
