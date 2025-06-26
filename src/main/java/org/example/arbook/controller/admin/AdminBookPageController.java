package org.example.arbook.controller.admin;

import org.example.arbook.model.dto.request.BookPageReq;
import org.example.arbook.model.dto.response.EntireBookRes;
import org.example.arbook.service.interfaces.admin.AdminBookPageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.example.arbook.util.ApiConstants.*;

@RestController
@RequestMapping(API + V1 + ADMIN + BOOK_PAGE)
public class AdminBookPageController {

    private final AdminBookPageService adminBookPageService;

    public AdminBookPageController(AdminBookPageService adminBookPageService) {
        this.adminBookPageService = adminBookPageService;
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<EntireBookRes> getEntireBook(@PathVariable Long bookId) {
        EntireBookRes entireBookRes = adminBookPageService.getEntireBook(bookId);
        return ResponseEntity.ok(entireBookRes);
    }

    @PostMapping
    public ResponseEntity<?> createBookPagesWithContents(@RequestBody BookPageReq bookPageReq){
return null;
    }

}
