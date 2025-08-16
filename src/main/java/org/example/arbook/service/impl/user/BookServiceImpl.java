package org.example.arbook.service.impl.user;

import lombok.RequiredArgsConstructor;
import org.example.arbook.exception.BookNotFoundException;
import org.example.arbook.exception.QrCodeException;
import org.example.arbook.model.dto.response.BookRes;
import org.example.arbook.model.dto.response.UserBookRes;
import org.example.arbook.model.entity.Book;
import org.example.arbook.model.entity.QrCode;
import org.example.arbook.model.entity.User;
import org.example.arbook.model.enums.BookStatus;
import org.example.arbook.model.enums.QrCodeStatus;
import org.example.arbook.model.mapper.BookMapper;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.repository.QrCodeRepository;
import org.example.arbook.service.interfaces.user.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final QrCodeRepository qrCodeRepository;

    @Override
    public List<BookRes> getActiveOnSaleBooks() {
        List<Book> books = bookRepository.findByIsActiveTrueAndStatus(BookStatus.ON_SALE);
        return books.stream().
                map(bookMapper::toBookResponse)
                .toList();
    }

    @Override
    public List<BookRes> getActiveOnSaleBooksByCategoryId(UUID categoryId) {
        List<Book> books = bookRepository.findByIsActiveTrueAndStatusAndCategoryId(BookStatus.ON_SALE, categoryId);
        return books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
    }

    @Override
    @Transactional
    public UserBookRes getUserBookByQrCodeUUID(User user, UUID qrCodeUUID) {
        QrCode qrCode = qrCodeRepository.findById(qrCodeUUID).orElseThrow(() ->  new QrCodeException("QrCode not found with UUID: " + qrCodeUUID));
        if (qrCode.getStatus().equals(QrCodeStatus.BLOCKED))
            throw new QrCodeException("QrCode Code is Blocked with UUID: " + qrCodeUUID);
        if (qrCode.getUserId() != null && !qrCode.getUserId().equals(user.getId()))
            throw new QrCodeException("QrCode is being used by User with ID: " + qrCode.getUserId());
        if (qrCode.getUserId() == null) {
            qrCode.setUserId(user.getId());
            qrCode.setStatus(QrCodeStatus.ACTIVE);
        }

        Book book = bookRepository.findById(qrCode.getBookId()).orElseThrow(() ->
                new BookNotFoundException("Qr Code Book not Found with ID : " + qrCode.getBookId()));
        return bookMapper.toUserBookResponse(book);
    }


}
