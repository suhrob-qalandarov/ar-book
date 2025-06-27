package org.example.arbook.service.interfaces.admin;

import org.example.arbook.model.dto.request.BookPageReq;
import org.example.arbook.model.dto.response.BookPageRes;
import org.example.arbook.model.dto.response.EntireBookRes;

public interface AdminBookPageService {
    EntireBookRes getEntireBook(Long bookId);

    BookPageRes createBookPageWithContents(BookPageReq bookPageReq);

    BookPageRes updateBookPageWithContents(Long bookPageId, BookPageReq bookPageReq);
}
