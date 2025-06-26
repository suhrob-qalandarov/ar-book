package org.example.arbook.model.mapper;

import org.example.arbook.model.dto.response.BookPageRes;
import org.example.arbook.model.entity.BookPage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {PageContentMapper.class})
public interface BookPageMapper {

    @Mapping(source = "markerPhoto.id", target = "markerPhotoId")
    @Mapping(source = "file3D.id", target = "file3DId")
    BookPageRes toBookPageRes(BookPage bookPage);

    List<BookPageRes> toBookPageResList(List<BookPage> bookPages);
}

