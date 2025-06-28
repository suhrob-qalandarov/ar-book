package org.example.arbook.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.arbook.exception.PageContentNotFoundException;
import org.example.arbook.model.dto.request.PageContentReq;
import org.example.arbook.model.dto.response.PageContentRes;
import org.example.arbook.model.entity.PageContent;
import org.example.arbook.model.mapper.PageContentMapper;
import org.example.arbook.repository.PageContentRepository;
import org.example.arbook.service.interfaces.admin.AdminPageContentService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminPageContentServiceImpl implements AdminPageContentService {

    private final PageContentMapper pageContentMapper;
    private final PageContentRepository pageContentRepository;


    @Override
    public PageContentRes updatePageContent(Long pageContentId, PageContentReq pageContentReq) {
        PageContent pageContent = pageContentRepository.findById(pageContentId).orElseThrow(
                () -> new PageContentNotFoundException("PageContent not found with ID: " + pageContentId)
        );
        pageContentMapper.updateFromDto(pageContentReq, pageContent);
        PageContent updatedPageContent = pageContentRepository.save(pageContent);
        return pageContentMapper.toPageContentRes(updatedPageContent);
    }

    @Override
    public PageContentRes getOnePageContent(Long pageContentId) {
        PageContent pageContent = pageContentRepository.findById(pageContentId).orElseThrow(
                () -> new PageContentNotFoundException("PageContent not found with ID: " + pageContentId)
        );
        return pageContentMapper.toPageContentRes(pageContent);
    }


}
