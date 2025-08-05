package org.example.arbook.model.dto.response;

import java.util.UUID;

public record UserPageContentRes(
        UUID id,
        String text,
        String audioUrl,
        UUID languageId
) {
}
