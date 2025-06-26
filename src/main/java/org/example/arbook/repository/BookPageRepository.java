package org.example.arbook.repository;

import org.example.arbook.model.entity.BookPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookPageRepository extends JpaRepository<BookPage, Long> {
    List<BookPage> findAllByBookId(Long bookId);
}