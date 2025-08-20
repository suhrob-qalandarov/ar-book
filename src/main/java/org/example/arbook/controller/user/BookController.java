package org.example.arbook.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.response.BookRes;
import org.example.arbook.model.dto.response.UserBookRes;
import org.example.arbook.model.entity.User;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.service.interfaces.user.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.example.arbook.util.ApiConstants.*;

@RestController
@Validated
@RequestMapping(API + V1 + BOOK)
@RequiredArgsConstructor
@Tag(name = "User Book Controller", description = "User Book management endpoints")
public class BookController {
    private final BookService bookService;
    private final BookRepository bookRepository;

    @Operation(summary = "Get all active and On Sale books", description = "Retrieves a list of all active and On Sale books")
    @GetMapping
    public ResponseEntity<List<BookRes>> getActiveOnSaleBooks() {
        List<BookRes> books = bookService.getActiveOnSaleBooks();
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }

    @Operation(summary = "Get active and On Sale books by category", description = "Retrieves a list of active and On Sale books for a given category ID")
    @GetMapping("/{categoryId}")
    public ResponseEntity<List<BookRes>> getActiveOnSaleBooksByCategoryId(
            @Parameter(description = "ID of the category") @PathVariable @Positive UUID categoryId) {
        List<BookRes> books = bookService.getActiveOnSaleBooksByCategoryId(categoryId);
        return books.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(books);
    }

    @GetMapping(USER + "/{qrCodeUUID}")
    public ResponseEntity<UserBookRes> getUsersBook(@AuthenticationPrincipal User user, @PathVariable UUID qrCodeUUID) {
        UserBookRes userBookRes = bookService.getUserBookByQrCodeUUID(user, qrCodeUUID);
        return ResponseEntity.ok(userBookRes);
    }

     @GetMapping(USER)
    public ResponseEntity<List<UserBookRes>> getAllUsersBooks(@AuthenticationPrincipal User user) {
        List<UserBookRes> userBookResList = bookService.getAllUserBooks(user);
        return ResponseEntity.ok(userBookResList);
    }




}
