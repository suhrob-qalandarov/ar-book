package org.example.arbook.model.dto.response;

import java.util.List;
import java.util.UUID;

public record UserBookPageRes(
        UUID id,
        UUID markerPatternId,
        UUID file3DId,
        UUID bookId,
        List<UserPageContentRes> pageContents
) {
}
