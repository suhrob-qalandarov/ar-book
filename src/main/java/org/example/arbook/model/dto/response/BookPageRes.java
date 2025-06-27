package org.example.arbook.model.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record BookPageRes(
        Long id,
        Long bookId,
        Long markerPhotoId,
        Long file3DId,
        List<PageContentRes> pageContents,

        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String createdBy,
        String updatedBy
) {
}
