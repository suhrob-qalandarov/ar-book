package org.example.arbook.model.dto.response;

import java.util.List;
import java.util.UUID;

public record UserBookPageRes(
        UUID id,
        UUID markerPatternUrl,
        UUID file3DUrl,
        UUID bookId,
        List<UserPageContentRes> pageContents
) {
}
