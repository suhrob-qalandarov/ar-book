package org.example.arbook.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.arbook.exception.BookNotFoundException;
import org.example.arbook.model.dto.request.BookPageReq;
import org.example.arbook.model.dto.request.PageContentReq;
import org.example.arbook.model.dto.response.BookPageRes;
import org.example.arbook.model.dto.response.EntireBookRes;
import org.example.arbook.model.entity.Book;
import org.example.arbook.model.entity.BookPage;
import org.example.arbook.model.entity.PageContent;
import org.example.arbook.model.mapper.BookMapper;
import org.example.arbook.model.mapper.BookPageMapper;
import org.example.arbook.repository.BookPageRepository;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.repository.PageContentRepository;
import org.example.arbook.service.interfaces.admin.AdminBookPageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBookPageServiceImpl implements AdminBookPageService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookPageMapper bookPageMapper;
    private final PageContentRepository pageContentRepository;
    private final BookPageRepository bookPageRepository;

    @Override
    @Transactional
    public EntireBookRes getEntireBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID: " + bookId)
        );
        return bookMapper.toEntireBookResponse(book);
    }

    @Override
    @Transactional
    public BookPageRes createBookPageWithContents(BookPageReq bookPageReq) {
        BookPage bookPage = bookPageMapper.toEntity(bookPageReq);

        List<PageContent> pageContents = bookPage.getPageContents().stream()
                .peek(pageContent ->
                        pageContent.setBookPage(bookPage)).toList();

        bookPage.setPageContents(pageContents);

        BookPage savedBookPage = bookPageRepository.save(bookPage);
        return bookPageMapper.toBookPageRes(savedBookPage);
    }

    @Override
    public BookPageRes updateBookPageWithContents(Long bookPageId, BookPageReq bookPageReq) {
        return null;
    }
}
