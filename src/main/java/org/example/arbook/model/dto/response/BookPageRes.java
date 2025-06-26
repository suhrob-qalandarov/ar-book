package org.example.arbook.model.dto.response;

import java.util.List;

public record BookPageRes(
        Long markerPhotoId,
        Long file3DId,
        List<PageContentRes> pageContents
) {
}
