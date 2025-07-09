package org.example.arbook.service.impl.user;

import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.response.BookRes;
import org.example.arbook.model.entity.Book;
import org.example.arbook.model.enums.BookStatus;
import org.example.arbook.model.mapper.BookMapper;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.service.interfaces.user.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookRes> getActiveOnSaleBooks() {
        List<Book> books = bookRepository.findByIsActiveTrueAndStatus(BookStatus.ON_SALE);
        return books.stream().
                map(bookMapper::toBookResponse)
                .toList();
    }

    @Override
    public List<BookRes> getActiveOnSaleBooksByCategoryId(Long categoryId) {
        List<Book> books = bookRepository.findByIsActiveTrueAndStatusAndCategoryId(BookStatus.ON_SALE, categoryId);
        return books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
    }


}
