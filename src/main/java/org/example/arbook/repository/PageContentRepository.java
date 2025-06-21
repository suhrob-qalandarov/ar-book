package org.example.arbook.repository;

import org.example.arbook.model.entity.PageContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageContentRepository extends JpaRepository<PageContent, Long> {
}