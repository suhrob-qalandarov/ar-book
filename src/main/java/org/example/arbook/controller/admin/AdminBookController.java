package org.example.arbook.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.BookReq;
import org.example.arbook.model.entity.Book;
import org.example.arbook.service.interfaces.admin.AdminBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> bookRes = adminBookService.getAllBooks();
        return ResponseEntity.ok(bookRes);
    }

    @Operation(summary = "Create a new book", description = "Creates a book with the provided details")
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookReq bookReq) {
        Book book = adminBookService.createBook(bookReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @Operation(summary = "Retrieve a particular book", description = "Returns a book by given Id")
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getOneBook(@PathVariable @Positive Long bookId) {
        Book book = adminBookService.getOneBook(bookId);
        return ResponseEntity.ok(book);
    }

    @Operation(summary = "Update a book by ID", description = "Updates and Returns book details for the given book ID.")
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable @Positive Long bookId, @Valid @RequestBody BookReq bookReq) {
        Book updatedBook = adminBookService.updateBook(bookId, bookReq);
        return ResponseEntity.ok(updatedBook);
    }

    @PatchMapping("/{bookId}")
    public ResponseEntity<?> activateOrDeactivateBook(@PathVariable Long bookId) {
        String message = adminBookService.activateOrDeactivateBook(bookId);
        return ResponseEntity.ok("Book " + message);
    }
}
