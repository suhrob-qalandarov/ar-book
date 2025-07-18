package org.example.arbook.model.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record BookPageWithNoContentReq(
        @NotNull(message = "MarkerPhoto ID cannot be null")
        UUID markerPhotoId,
        @NotNull(message = "File3D ID cannot be null")
        UUID file3DId,
        @NotNull(message = "Book ID cannot be null")
        UUID bookId
) {
}
