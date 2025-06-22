package org.example.arbook.model.mapper;

import org.example.arbook.model.dto.request.BookReq;
import org.example.arbook.model.dto.response.BookRes;
import org.example.arbook.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "attachment.fileUrl", target = "attachmentUrl")
    BookRes toBookResponse(Book book);

    Book toBook(BookReq bookReq);
}
