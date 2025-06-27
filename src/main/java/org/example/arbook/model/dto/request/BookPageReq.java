package org.example.arbook.model.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookPageReq(
        @NotNull(message = "Book ID cannot be null")
        Long bookId,

        @NotNull(message = "Marker photo ID cannot be null")
        Long markerPhotoId,

        @NotNull(message = "3D file ID cannot be null")
        Long file3DId,

        @NotEmpty(message = "Page contents must not be empty")
        @Valid
        List<@Valid PageContentReq> pageContents
) {
}
