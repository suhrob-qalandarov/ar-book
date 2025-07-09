package org.example.arbook.model.mapper;

import org.example.arbook.component.BookPageMapperHelper;
import org.example.arbook.model.dto.request.PageContentReq;
import org.example.arbook.model.dto.response.PageContentRes;
import org.example.arbook.model.dto.response.UserPageContentRes;
import org.example.arbook.model.entity.PageContent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookPageMapperHelper.class})
public interface PageContentMapper {

    @Mapping(source = "audio.id", target = "audioId")
    @Mapping(source = "language.id", target = "languageId")
    PageContentRes toPageContentRes(PageContent pageContent);

    @Mapping(source = "audio.id", target = "audioId")
    @Mapping(source = "language.id", target = "languageId")
    UserPageContentRes toUserPageContentResponse(PageContent pageContent);

    List<PageContentRes> toPageContentResList(List<PageContent> contents);

    @Mapping(target = "audio", source = "audioId", qualifiedByName = "mapAttachment")
    @Mapping(target = "language", source = "languageId", qualifiedByName = "mapLanguage")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "bookPage", ignore = true)
    PageContent toEntity(PageContentReq req);


    List<PageContent> toPageContentList(List<PageContentReq> pageContentReqs);

    @Mapping(target = "audio", source = "audioId", qualifiedByName = "mapAttachment")
    @Mapping(target = "language", source = "languageId", qualifiedByName = "mapLanguage")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "bookPage", ignore = true)
    void updateFromDto(PageContentReq req, @MappingTarget PageContent pageContent);


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

}

