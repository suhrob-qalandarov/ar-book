package org.example.arbook.service.impl.admin;

import lombok.RequiredArgsConstructor;
import org.example.arbook.exception.AttachmentNotFoundException;
import org.example.arbook.exception.BookPageNotFoundException;
import org.example.arbook.model.dto.request.BookPageReq;
import org.example.arbook.model.dto.request.BookPageWithNoContentReq;
import org.example.arbook.model.dto.response.BookPageRes;
import org.example.arbook.model.entity.Attachment;
import org.example.arbook.model.entity.BookPage;
import org.example.arbook.model.entity.PageContent;
import org.example.arbook.model.mapper.BookMapper;
import org.example.arbook.model.mapper.BookPageMapper;
import org.example.arbook.repository.AttachmentRepository;
import org.example.arbook.repository.BookPageRepository;
import org.example.arbook.repository.BookRepository;
import org.example.arbook.repository.PageContentRepository;
import org.example.arbook.service.interfaces.admin.AdminBookPageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminBookPageServiceImpl implements AdminBookPageService {
    private final BookPageMapper bookPageMapper;
    private final BookPageRepository bookPageRepository;
    private final AttachmentRepository attachmentRepository;


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
    @Transactional
    public String enableOrDisableBookPage(UUID bookPageId) {
        BookPage bookPage = bookPageRepository.findById(bookPageId).orElseThrow(() ->
                new BookPageNotFoundException("BookPage not found with ID : " + bookPageId));

        bookPage.setIsActive(!bookPage.getIsActive());
        bookPageRepository.save(bookPage);
        return bookPage.getIsActive() ? "Activated" : "Deactivated";
    }

    @Override
    @Transactional
    public BookPageRes updateBookPageWithNoContent(UUID bookPageId, BookPageWithNoContentReq bookPageWithNoContentReq) {
        BookPage bookPage = bookPageRepository.findById(bookPageId).orElseThrow(() ->
                new BookPageNotFoundException("BookPage not found with ID : " + bookPageId)
        );
        Attachment file3D = attachmentRepository.findById(bookPageWithNoContentReq.file3DId()).orElseThrow(() ->
                new AttachmentNotFoundException("Attachment not found with ID : " + bookPageId)
        );
        Attachment markerPhoto = attachmentRepository.findById(bookPageWithNoContentReq.markerPhotoId()).orElseThrow(() ->
                new AttachmentNotFoundException("Attachment not found with ID : " + bookPageId)
        );
        bookPage.setFile3D(file3D);
        bookPage.setMarkerPhoto(markerPhoto);
        return bookPageMapper.toBookPageRes(bookPage);
    }




    @Override
    public BookPageRes getOneBookPageWithContents(UUID bookPageId) {
        BookPage bookPage = bookPageRepository.findById(bookPageId).orElseThrow(() ->
                new BookPageNotFoundException("BookPage not found with ID : " + bookPageId));

        System.out.println(bookPage.toString());
        System.out.println("✅✅✅");
        System.out.println("✅✅✅");
        System.out.println("✅✅✅");
        System.out.println("✅✅✅");
        return bookPageMapper.toBookPageRes(bookPage);
    }

}
