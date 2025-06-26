package org.example.arbook.repository;

import org.example.arbook.model.entity.Book;
import org.example.arbook.model.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findByIsActiveTrueAndStatus(BookStatus status);

  List<Book> findByIsActiveTrueAndStatusAndCategoryId(BookStatus status, Long categoryId);
  @Query("""
    SELECT b FROM Book b
    LEFT JOIN FETCH b.attachment
    LEFT JOIN FETCH b.category
    LEFT JOIN FETCH b.bookPages bp
    LEFT JOIN FETCH bp.markerPhoto
    LEFT JOIN FETCH bp.file3D
    LEFT JOIN FETCH bp.pageContents pc
    LEFT JOIN FETCH pc.audio
    LEFT JOIN FETCH pc.language
    WHERE b.id = :id
""")
  Optional<Book> findByIdWithPages(@Param("id") Long id);

}