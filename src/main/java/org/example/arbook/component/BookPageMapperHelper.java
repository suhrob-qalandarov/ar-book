package org.example.arbook.component;


import lombok.RequiredArgsConstructor;
import org.example.arbook.exception.AttachmentNotFoundException;
import org.example.arbook.exception.BookNotFoundException;
import org.example.arbook.exception.LanguageNotFoundException;
import org.example.arbook.model.entity.Attachment;
import org.example.arbook.model.entity.Book;
import org.example.arbook.model.entity.Language;
import org.example.arbook.repository.AttachmentRepository;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.repository.LanguageRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class    BookPageMapperHelper {

    private final AttachmentRepository attachmentRepository;
    private final LanguageRepository languageRepository;
    private final BookRepository bookRepository;

    @Named("mapAttachment")
    public Attachment mapAttachment(UUID id) {
        if (id == null) return null;
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new AttachmentNotFoundException("Attachment not found with ID: " + id));
    }

    @Named("mapLanguage")
    public Language mapLanguage(UUID id) {
        if (id == null) return null;
        return languageRepository.findById(id)
                .orElseThrow(() -> new LanguageNotFoundException("Language not found with ID: " + id));
    }

    @Named("mapBook")
    public Book mapBook(UUID id) {
        if (id == null) return null;
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + id));}
}
