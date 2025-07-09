package org.example.arbook.repository;

import org.example.arbook.model.entity.Book;
import org.example.arbook.model.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findByIsActiveTrueAndStatus(BookStatus status);

  List<Book> findByIsActiveTrueAndStatusAndCategoryId(BookStatus status, Long categoryId);

    boolean existsBookByIdIs(Long id);
}