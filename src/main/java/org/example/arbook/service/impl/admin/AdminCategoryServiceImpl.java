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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category getCategory(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll(); // for admin
    }

    @Override
    @Transactional
    public Category addCategory(CategoryReq categoryReq) {
        if (categoryRepository.existsByName(categoryReq.name())) {
            throw new IllegalArgumentException("Category already exists with name: " + categoryReq.name());
        }
        Category category = convertToCategory(categoryReq);
       return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void updateCategory(UUID id, CategoryReq categoryReq) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Category not found with id: " + id));

        if (!categoryReq.name().equals(existingCategory.getName()) &&
                categoryRepository.existsByName(categoryReq.name().trim())
        ) {
            throw new IllegalArgumentException("Category already exists with name: " + categoryReq.name());
        }

        updateCategoryFields(existingCategory, categoryReq);
        categoryRepository.save(existingCategory);
    }

    @Override
    public String activateOrDeactivateCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("not found with id: " + id));

        boolean newStatus = !category.getIsActive();
        category.setIsActive(newStatus);
        categoryRepository.save(category);

        return newStatus ? "activated" : "deactivated";
    }

    private void updateCategoryFields(Category category, CategoryReq categoryUpdateReq) {
        if (!categoryUpdateReq.name().isBlank()) category.setName(categoryUpdateReq.name().trim());
    }

    private Category convertToCategory(CategoryReq categoryReq) {
        Category category = new Category();
        if (!categoryReq.name().isBlank()) category.setName(categoryReq.name().trim());
        return category;
    }
}
