package org.example.arbook.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record BookPageReq(
        @NotNull(message = "Book ID cannot be null")
        UUID bookId,

        @NotNull(message = "Marker photo ID cannot be null")
        UUID markerPhotoId,

        @NotNull(message = "MarkerPattern ID cannot be null")
        UUID markerPatternId,

        @NotNull(message = "3D file ID cannot be null")
        UUID file3DId,

        @NotEmpty(message = "Page contents must not be empty")
        @Valid
        List<@Valid PageContentReq> pageContents
) {
}
