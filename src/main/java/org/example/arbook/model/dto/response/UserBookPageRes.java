package org.example.arbook.model.dto.response;

import java.util.List;

public record UserBookPageRes(
        Long id,
        Long markerPatternId,
        Long file3DId,
        Long bookId,
        List<UserPageContentRes> pageContents
) {
}
