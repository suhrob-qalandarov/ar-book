package org.example.arbook.repository;

import org.example.arbook.model.entity.PageContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PageContentRepository extends JpaRepository<PageContent, UUID> {
}