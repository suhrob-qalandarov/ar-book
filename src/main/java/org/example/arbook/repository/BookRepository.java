package org.example.arbook.repository;

import org.example.arbook.model.entity.Book;
import org.example.arbook.model.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByIsActiveTrueAndStatus(BookStatus status);

    List<Book> findByIsActiveTrueAndStatusAndCategoryId(BookStatus status, UUID categoryId);

    boolean existsBookByIdIs(UUID id);

    List<Book> findAllByIdIn(List<UUID> ids);
}