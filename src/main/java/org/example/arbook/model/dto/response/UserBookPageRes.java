package org.example.arbook.model.dto.response;

import java.util.List;
import java.util.UUID;

public record UserBookPageRes(
        UUID id,
        String markerPatternUrl,
        String file3DUrl,
        String bookId,
        List<UserPageContentRes> pageContents
) {
}
