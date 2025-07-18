package org.example.arbook.service.interfaces.admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.example.arbook.model.dto.request.BookPageReq;
import org.example.arbook.model.dto.request.BookPageWithNoContentReq;
import org.example.arbook.model.dto.response.BookPageRes;

import java.util.UUID;

public interface AdminBookPageService {

    BookPageRes createBookPageWithContents(BookPageReq bookPageReq);


    String enableOrDisableBookPage(@Positive UUID bookPageId);

    BookPageRes updateBookPageWithNoContent(@Positive UUID bookPageId, @Valid BookPageWithNoContentReq bookPageWithNoContentReq);

    BookPageRes getOneBookPageWithContents(@Positive UUID bookPageId);
}
