package org.example.arbook.service.interfaces.admin;

import jakarta.validation.Valid;
import org.example.arbook.model.dto.request.BookReq;
import org.example.arbook.model.dto.request.BookStatusChangeReq;
import org.example.arbook.model.dto.response.AdminBookRes;
import org.example.arbook.model.dto.response.EntireBookRes;
import org.example.arbook.model.entity.Book;
import org.example.arbook.model.enums.BookStatus;

import java.util.List;

public interface AdminBookService {
    List<AdminBookRes   > getAllBooks();

    AdminBookRes updateBook(Long bookId, BookReq bookReq);

    AdminBookRes createBook(@Valid BookReq bookReq);

    AdminBookRes getOneBook(Long bookId);

    String activateOrDeactivateBook(Long bookId);

    BookStatus changeBookStatus(Long bookId, @Valid BookStatusChangeReq bookStatusChangeReq);

    EntireBookRes getEntireBook(Long bookId);
}
