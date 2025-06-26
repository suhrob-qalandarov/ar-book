package org.example.arbook.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.arbook.exception.BookNotFoundException;
import org.example.arbook.model.dto.response.EntireBookRes;
import org.example.arbook.model.entity.Book;
import org.example.arbook.model.mapper.BookMapper;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.service.interfaces.admin.AdminBookPageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminBookPageServiceImpl implements AdminBookPageService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    @Transactional
    public EntireBookRes getEntireBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID: " + bookId)
        );
        return bookMapper.toEntireBookResponse(book);
    }
}
