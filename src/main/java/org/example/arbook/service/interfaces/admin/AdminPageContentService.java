package org.example.arbook.service.interfaces.admin;


import jakarta.validation.constraints.Positive;
import org.example.arbook.model.dto.request.PageContentReq;
import org.example.arbook.model.dto.response.PageContentRes;

public interface AdminPageContentService {

    PageContentRes updatePageContent(@Positive Long pageContentId, PageContentReq pageContentReq);

    PageContentRes getOnePageContent(@Positive Long pageContentId);
}
