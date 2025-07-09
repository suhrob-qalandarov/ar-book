package org.example.arbook.service.interfaces.user;

import org.example.arbook.model.dto.response.BookRes;
import org.example.arbook.model.dto.response.UserBookRes;
import org.example.arbook.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<BookRes> getActiveOnSaleBooks();

    List<BookRes> getActiveOnSaleBooksByCategoryId(Long categoryId);

    UserBookRes getUserBookByQrCodeUUID(User user, UUID qrCodeUUID);
}
