package org.example.arbook.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.BookReq;
import org.example.arbook.model.dto.request.BookPageStatusChangeReq;
import org.example.arbook.model.dto.response.AdminBookRes;
import org.example.arbook.model.dto.response.EntireBookRes;
import org.example.arbook.model.enums.BookStatus;
import org.example.arbook.service.interfaces.admin.AdminBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.example.arbook.util.ApiConstants.*;

@Tag(name = "Admin Book Controller", description = "API for managing books in the admin panel")
@Validated
@RestController
@RequestMapping(API + V1 + ADMIN + BOOK)
@RequiredArgsConstructor
public class AdminBookController {
    private final AdminBookService adminBookService;

    @Operation(summary = "Retrieve all books", description = "Returns a list of all books")
    @GetMapping
    public ResponseEntity<List<AdminBookRes>> getAllBooks() {
        List<AdminBookRes> bookRes = adminBookService.getAllBooks();
        return ResponseEntity.ok(bookRes);
    }

    @Operation(summary = "Create a new book", description = "Creates a book with the provided details")
    @PostMapping
    public ResponseEntity<AdminBookRes> createBook(@Valid @RequestBody BookReq bookReq) {
        AdminBookRes book = adminBookService.createBook(bookReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @Operation(summary = "Retrieve a particular book without its pages", description = "Returns a book(without its page contents) by given Id")
    @GetMapping("/{bookId}")
    public ResponseEntity<AdminBookRes> getOneBook(@PathVariable UUID bookId) {
        AdminBookRes book = adminBookService.getOneBook(bookId);
        return ResponseEntity.ok(book);
    }

    @GetMapping(ENTIRE + "/{bookId}")
    public ResponseEntity<EntireBookRes> getEntireBook(@PathVariable UUID bookId) {
        EntireBookRes entireBookRes = adminBookService.getEntireBook(bookId);
        return ResponseEntity.ok(entireBookRes);
    }

    @Operation(summary = "Update a book by ID", description = "Updates and Returns book details for the given book ID.")
    @PutMapping("/{bookId}")
    public ResponseEntity<AdminBookRes> updateBook(@PathVariable UUID bookId, @Valid @RequestBody BookReq bookReq) {
        AdminBookRes updatedBook = adminBookService.updateBook(bookId, bookReq);
        return ResponseEntity.ok(updatedBook);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<?> activateOrDeactivateBook(@PathVariable UUID bookId) {
        String message = adminBookService.activateOrDeactivateBook(bookId);
        return ResponseEntity.ok("Book " + message);
    }

    @PatchMapping("/{bookId}" + STATUS)
    public ResponseEntity<?> changeBookStatus(@PathVariable UUID bookId, @Valid @RequestBody BookPageStatusChangeReq bookStatusChangeReq) {
        BookStatus bookStatus = adminBookService.changeBookStatus(bookId, bookStatusChangeReq);
        return ResponseEntity.ok("Book status changed to " + bookStatus.name());
    }


}
