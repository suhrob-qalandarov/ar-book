package org.example.arbook.service.interfaces.admin;

import jakarta.validation.Valid;
import org.example.arbook.model.dto.request.BookReq;
import org.example.arbook.model.dto.request.BookPageStatusChangeReq;
import org.example.arbook.model.dto.response.AdminBookRes;
import org.example.arbook.model.dto.response.EntireBookRes;
import org.example.arbook.model.enums.BookStatus;

import java.util.List;
import java.util.UUID;

public interface AdminBookService {
    List<AdminBookRes   > getAllBooks();

    AdminBookRes updateBook(UUID bookId, BookReq bookReq);

    AdminBookRes createBook(@Valid BookReq bookReq);

    AdminBookRes getOneBook(UUID bookId);

    String activateOrDeactivateBook(UUID bookId);

    BookStatus changeBookStatus(UUID bookId, @Valid BookPageStatusChangeReq bookStatusChangeReq);

    EntireBookRes getEntireBook(UUID bookId);
}
