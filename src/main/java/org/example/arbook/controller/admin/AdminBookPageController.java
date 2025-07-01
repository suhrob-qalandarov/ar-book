package org.example.arbook.controller.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.BookPageReq;
import org.example.arbook.model.dto.request.BookPageWithNoContentReq;
import org.example.arbook.model.dto.response.BookPageRes;
import org.example.arbook.service.interfaces.admin.AdminBookPageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.example.arbook.util.ApiConstants.*;

@Validated
@RestController
@RequestMapping(API + V1 + ADMIN + BOOK_PAGE)
@RequiredArgsConstructor
public class AdminBookPageController {

    private final AdminBookPageService adminBookPageService;


    @PostMapping
    public ResponseEntity<BookPageRes> createBookPageWithContents(@Valid @RequestBody BookPageReq bookPageReq) {
        BookPageRes bookPageRes = adminBookPageService.createBookPageWithContents(bookPageReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookPageRes);
    }

    @GetMapping("/{bookPageId}")
    public ResponseEntity<BookPageRes> getOneBookPageWithContents(@PathVariable @Positive Long bookPageId) {
        BookPageRes bookPageRes = adminBookPageService.getOneBookPageWithContents(bookPageId);
        return ResponseEntity.ok(bookPageRes);
    }

    @PutMapping("/{bookPageId}")
    public ResponseEntity<BookPageRes> updateBookPageWithNoContent(@PathVariable @Positive Long bookPageId, @Valid @RequestBody BookPageWithNoContentReq bookPageWithNoContentReq) {
        BookPageRes updatedBookPageRes = adminBookPageService.updateBookPageWithNoContent(bookPageId, bookPageWithNoContentReq);
        return ResponseEntity.ok(updatedBookPageRes);
    }



    @PatchMapping("/{bookPageId}")
    public ResponseEntity<String> enableOrDisableBookPage(@PathVariable @Positive Long bookPageId) {
        String message = adminBookPageService.enableOrDisableBookPage(bookPageId);
        return ResponseEntity.ok("BookPage " + message);
    }


}
