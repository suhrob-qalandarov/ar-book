package org.example.arbook.model.dto.request;

import java.util.List;

public record BookPageReq(
        Long bookId,
        Long markerPhotoId,
        Long file3DId,
        List<PageContentReq> pageContents


){

}
