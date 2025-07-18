package org.example.arbook.service.interfaces.admin;


import jakarta.validation.constraints.Positive;
import org.example.arbook.model.dto.request.PageContentReq;
import org.example.arbook.model.dto.response.PageContentRes;

import java.util.UUID;

public interface AdminPageContentService {

    PageContentRes updatePageContent(@Positive UUID pageContentId, PageContentReq pageContentReq);

    PageContentRes getOnePageContent(@Positive UUID pageContentId);
}
