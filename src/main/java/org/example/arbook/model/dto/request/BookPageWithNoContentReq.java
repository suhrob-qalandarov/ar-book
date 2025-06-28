package org.example.arbook.model.dto.request;

import jakarta.validation.constraints.NotNull;

public record BookPageWithNoContentReq(
        @NotNull(message = "MarkerPhoto ID cannot be null")
        Long markerPhotoId,
        @NotNull(message = "File3D ID cannot be null")
        Long file3DId,
        @NotNull(message = "Book ID cannot be null")
        Long bookId
) {
}
