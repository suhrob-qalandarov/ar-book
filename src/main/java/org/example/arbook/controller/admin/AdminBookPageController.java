package org.example.arbook.controller.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.arbook.model.dto.request.BookPageReq;
import org.example.arbook.model.dto.response.BookPageRes;
import org.example.arbook.model.dto.response.EntireBookRes;
import org.example.arbook.service.interfaces.admin.AdminBookPageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.example.arbook.util.ApiConstants.*;

@Validated
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
    public ResponseEntity<BookPageRes> createBookPageWithContents(@Valid@RequestBody BookPageReq bookPageReq) {
        BookPageRes bookPageRes = adminBookPageService.createBookPageWithContents(bookPageReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookPageRes);
    }

    @PutMapping("/{bookPageId}")
    public ResponseEntity<?> updateBookWithContents(@PathVariable @Positive Long bookPageId, @Valid @RequestBody BookPageReq bookPageReq) {
        BookPageRes updatedBookPageRes = adminBookPageService.updateBookPageWithContents(bookPageId, bookPageReq);
        return ResponseEntity.ok(updatedBookPageRes);
    }



}
