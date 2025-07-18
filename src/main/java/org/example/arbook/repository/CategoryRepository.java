package org.example.arbook.repository;

import org.example.arbook.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findAllByIsActiveIsTrue();

    boolean existsByName(String name);
}