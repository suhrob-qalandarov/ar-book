package org.example.arbook.model.mapper;

import org.example.arbook.component.BookPageMapperHelper;
import org.example.arbook.model.dto.request.BookPageReq;
import org.example.arbook.model.dto.response.BookPageRes;
import org.example.arbook.model.entity.BookPage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = { PageContentMapper.class,BookPageMapperHelper.class})
public interface BookPageMapper {

    @Mapping(source = "markerPhoto.id", target = "markerPhotoId")
    @Mapping(source = "markerPattern.id", target = "markerPatternId")
    @Mapping(source = "file3D.id", target = "file3DId")
    @Mapping(source = "book.id", target = "bookId")
    BookPageRes toBookPageRes(BookPage bookPage);

    List<BookPageRes> toBookPageResList(List<BookPage> bookPages);

    @Mapping(target = "book", source = "bookId", qualifiedByName = "mapBook")
    @Mapping(target = "markerPhoto", source = "markerPhotoId", qualifiedByName = "mapAttachment")
    @Mapping(target = "markerPattern", source = "markerPatternId", qualifiedByName = "mapAttachment")
    @Mapping(target = "file3D", source = "file3DId", qualifiedByName = "mapAttachment")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    BookPage toEntity(BookPageReq req);



}

