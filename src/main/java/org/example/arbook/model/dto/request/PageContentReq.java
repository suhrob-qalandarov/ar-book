package org.example.arbook.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PageContentReq(
        @NotBlank(message = "Text of the audio must not be blank")
        String text,

        @NotNull(message = "Audio ID cannot be null")
        Long audioId,

        @NotNull(message = "Language ID cannot be null")
        Long languageId
) {
}

