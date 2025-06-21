package org.example.arbook.service.interfaces.user;

import org.example.arbook.model.dto.response.BookRes;

import java.util.List;

public interface BookService {
    List<BookRes> getActiveCompletedBooks();

    List<BookRes> getActiveCompletedBooksByCategoryId(Long categoryId);
}
