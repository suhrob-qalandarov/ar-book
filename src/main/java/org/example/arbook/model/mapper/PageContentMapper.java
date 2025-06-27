package org.example.arbook.model.mapper;

import org.example.arbook.model.dto.request.PageContentReq;
import org.example.arbook.model.dto.response.PageContentRes;
import org.example.arbook.model.entity.PageContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageContentMapper {

    @Mapping(source = "audio.id", target = "audioId")
    @Mapping(source = "language.id", target = "languageId")
    PageContentRes toPageContentRes(PageContent pageContent);

    List<PageContentRes> toPageContentResList(List<PageContent> contents);

//    @Mapping(source = "audioId", target = "audio.id")
//    @Mapping(source = "languageId", target = "language.id")
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "createdBy", ignore = true)
//    @Mapping(target = "updatedBy", ignore = true)
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "isActive", ignore = true)
//    @Mapping(target = "bookPage", ignore = true)
//    PageContent toPageContent(PageContentReq pageContentReq);

    List<PageContent> toPageContentList(List<PageContent> pageContents);
}

