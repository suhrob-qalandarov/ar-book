package org.example.arbook.model.mapper;

import org.example.arbook.model.dto.request.BookPageReq;
import org.example.arbook.model.dto.request.BookReq;
import org.example.arbook.model.dto.response.AdminBookRes;
import org.example.arbook.model.dto.response.BookRes;
import org.example.arbook.model.dto.response.EntireBookRes;
import org.example.arbook.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookPageMapper.class})
public interface BookMapper {

    @Mapping(source = "attachment.id", target = "attachmentId")
    BookRes toBookResponse(Book book);
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "attachmentId", target = "attachment.id")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "bookPages", ignore = true)
    Book toBook(BookReq bookReq);

    @Mapping(source = "id", target = "bookId")
    @Mapping(source = "status", target = "bookStatus")
    @Mapping(source = "attachment.id", target = "bookCoverId")
    @Mapping(source = "bookPages", target = "bookPages")
        // <- this was missing
    EntireBookRes toEntireBookResponse(Book book);

    List<EntireBookRes> toEntireBookResponseList(List<Book> books);

    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "attachment.id", target = "attachmentId")
    AdminBookRes toAdminBookResponse(Book book);

    List<AdminBookRes> toAdminBookResponseList(List<Book> books);


}
