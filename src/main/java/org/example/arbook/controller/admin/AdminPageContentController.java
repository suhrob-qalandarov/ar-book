package org.example.arbook.controller.admin;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.PageContentReq;
import org.example.arbook.model.dto.response.PageContentRes;
import org.example.arbook.service.interfaces.admin.AdminPageContentService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.example.arbook.util.ApiConstants.*;

@Validated
@RestController
@RequestMapping(API + V1 + ADMIN + PAGE_CONTENT)
@RequiredArgsConstructor
public class AdminPageContentController {

    private final AdminPageContentService adminPageContentService;


    @PutMapping("/{pageContentId}")
    public ResponseEntity<PageContentRes> updatePageContent(@PathVariable @Positive UUID pageContentId,
                                                            @RequestBody PageContentReq pageContentReq) {
        PageContentRes updatedPageContentRes = adminPageContentService.updatePageContent(pageContentId, pageContentReq);
        return ResponseEntity.ok(updatedPageContentRes);
    }

    @GetMapping("/{pageContentId}")
    public ResponseEntity<PageContentRes> getOnePageContent(@PathVariable @Positive UUID pageContentId) {
        PageContentRes pageContentRes = adminPageContentService.getOnePageContent(pageContentId);
        return ResponseEntity.ok(pageContentRes);
    }


}
