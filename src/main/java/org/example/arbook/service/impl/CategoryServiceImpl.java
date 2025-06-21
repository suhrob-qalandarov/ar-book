package org.example.arbook.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.arbook.model.dto.response.CategoryRes;
import org.example.arbook.model.entity.Category;
import org.example.arbook.repository.CategoryRepository;
import org.example.arbook.service.interfaces.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryRes> getActiveCategories() {
        return categoryRepository.findAllByIsActiveIsTrue().stream()
                .map(this::convertToCategoryRes)
                .toList();
    }

    private CategoryRes convertToCategoryRes(Category category) {
        return CategoryRes.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
