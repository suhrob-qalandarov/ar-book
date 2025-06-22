package org.example.arbook.service.interfaces.admin;

import jakarta.validation.Valid;
import org.example.arbook.model.dto.request.BookReq;
import org.example.arbook.model.dto.response.BookRes;
import org.example.arbook.model.entity.Book;

import java.util.List;

public interface AdminBookService {
    List<BookRes> getAllBooks();

    Book updateBook(Long bookId, BookReq bookReq);

    Book createBook(@Valid BookReq bookReq);

    Book getOneBook(Long bookId);
}
