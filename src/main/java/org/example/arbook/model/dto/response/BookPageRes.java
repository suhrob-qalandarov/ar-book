package org.example.arbook.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record BookPageRes(
        UUID id,
        UUID bookId,
        UUID markerPhotoId,
        UUID markerPatternId,
        UUID file3DId,
        List<PageContentRes> pageContents,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}
