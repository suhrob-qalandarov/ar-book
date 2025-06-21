package org.example.arbook.repository;

import org.example.arbook.model.entity.BookPage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookPageRepository extends JpaRepository<BookPage, Long> {
}