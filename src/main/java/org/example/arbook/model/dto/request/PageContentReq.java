package org.example.arbook.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PageContentReq(
        @NotBlank(message = "Text of the audio must not be blank")
        String text,

        @NotNull(message = "Audio ID cannot be null")
        UUID audioId,

        @NotNull(message = "Language ID cannot be null")
        UUID languageId
) {
}

