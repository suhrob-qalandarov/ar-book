package org.example.arbook.service.impl.admin;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.request.CategoryReq;
import org.example.arbook.model.entity.Category;
import org.example.arbook.repository.CategoryRepository;
import org.example.arbook.service.interfaces.admin.AdminCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAllByIsActiveIsTrue();
    }

    @Override
    @Transactional
    public void addCategory(CategoryReq categoryReq) {
        if (categoryRepository.existsByName(categoryReq.name())) {
            throw new IllegalArgumentException("Category already exists with name: " + categoryReq.name());
        }
        Category category = convertToCategory(categoryReq);
        Category savedCategory = categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(Long id, CategoryReq categoryReq) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));

        if (!categoryReq.name().trim().equals(existingCategory.getName()) &&
                categoryRepository.existsByName(categoryReq.name().trim())
        ) {
            throw new IllegalArgumentException("Category already exists with name: " + categoryReq.name());
        }

        updateCategoryFields(existingCategory, categoryReq);
        categoryRepository.save(existingCategory);
    }

    private void updateCategoryFields(Category category, CategoryReq categoryReq) {
        if (!categoryReq.name().isBlank()) category.setName(categoryReq.name().trim());
        category.setIsActive(categoryReq.isActive());
    }

    private Category convertToCategory(CategoryReq categoryReq) {
        Category category = new Category();
        if (!categoryReq.name().isBlank()) category.setName(categoryReq.name().trim());
        category.setIsActive(categoryReq.isActive());
        return category;
    }
}
