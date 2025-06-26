package org.example.arbook.model.mapper;

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
}

