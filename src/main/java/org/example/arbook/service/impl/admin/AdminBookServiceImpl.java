package org.example.arbook.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.arbook.exception.AttachmentNotFoundException;
import org.example.arbook.exception.BookNotFoundException;
import org.example.arbook.exception.CategoryNotFoundException;
import org.example.arbook.model.dto.request.BookReq;
import org.example.arbook.model.dto.response.BookRes;
import org.example.arbook.model.entity.Attachment;
import org.example.arbook.model.entity.Book;
import org.example.arbook.model.entity.Category;
import org.example.arbook.model.mapper.BookMapper;
import org.example.arbook.repository.AttachmentRepository;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.repository.CategoryRepository;
import org.example.arbook.service.interfaces.admin.AdminBookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBookServiceImpl implements AdminBookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public List<BookRes> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::toBookResponse).toList();
    }


    @Override
    public Book getOneBook(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Book not found with ID " + bookId));
    }


    @Override
    public Book updateBook(Long bookId, BookReq bookReq) {
        Book book = bookMapper.toBook(bookReq);
        book.setId(bookId);

        Category category = categoryRepository.findById(bookReq.categoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException("Category not found with ID: " + bookReq.categoryId()));
        book.setCategory(category);

        Attachment attachment = attachmentRepository.findById(bookReq.attachmentId())
                .orElseThrow(() ->
                        new AttachmentNotFoundException("Attachment not found with ID: " + bookReq.attachmentId()));

        book.setAttachment(attachment);

        return bookRepository.save(book);
    }


    @Override
    @Transactional
    public Book createBook(BookReq bookReq) {
        Book book = bookMapper.toBook(bookReq);

        Category category = categoryRepository.findById(bookReq.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + bookReq.categoryId()));
        book.setCategory(category);

        Attachment attachment = attachmentRepository.findById(bookReq.attachmentId())
                .orElseThrow(() -> new AttachmentNotFoundException("Attachment not found with ID: " + bookReq.attachmentId()));
        book.setAttachment(attachment);

        return bookRepository.save(book);
    }
}
