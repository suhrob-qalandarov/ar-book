package org.example.arbook.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.response.BookRes;
import org.example.arbook.model.entity.Book;
import org.example.arbook.service.interfaces.user.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.example.arbook.util.ApiConstants.*;

@RestController
@RequestMapping(API + V1 + USER + BOOK)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Get all active and completed books", description = "Retrieves a list of all active and completed books")
    @ApiResponse(responseCode = "200", description = "List of active and completed books returned successfully")
    @ApiResponse(responseCode = "204", description = "No active or completed books found")
    @GetMapping
    public ResponseEntity<List<BookRes>> getActiveCompletedBooks() {
        List<BookRes> books = bookService.getActiveCompletedBooks();
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }

    @Operation(summary = "Get active and completed books by category", description = "Retrieves a list of active and completed books for a given category ID")
    @ApiResponse(responseCode = "200", description = "List of active and completed books returned successfully")
    @ApiResponse(responseCode = "404", description = "No books found for the specified category")
    @ApiResponse(responseCode = "400", description = "Invalid category ID provided")
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<BookRes>> getActiveCompletedBooksByCategoryId(
            @Parameter(description = "ID of the category") @PathVariable Long categoryId) {
        List<BookRes> books = bookService.getActiveCompletedBooksByCategoryId(categoryId);
        return books.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(books);
    }
}
