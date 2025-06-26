package org.example.arbook.service.interfaces.admin;

import org.example.arbook.model.dto.response.EntireBookRes;

public interface AdminBookPageService {
    EntireBookRes getEntireBook(Long bookId);
}
