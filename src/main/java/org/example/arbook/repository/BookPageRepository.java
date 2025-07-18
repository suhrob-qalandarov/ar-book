package org.example.arbook.repository;

import org.example.arbook.model.entity.BookPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookPageRepository extends JpaRepository<BookPage, UUID> {
    List<BookPage> findAllByBookId(UUID bookId);
}