package org.example.arbook.service.interfaces.admin;

import org.example.arbook.model.dto.request.CategoryReq;
import org.example.arbook.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface AdminCategoryService {

    Category getCategory(UUID id);

    List<Category> getCategories();

    Category addCategory(CategoryReq categoryReq);

    void updateCategory(UUID categoryId, CategoryReq categoryUpdateReq);

    String activateOrDeactivateCategory(UUID id);
}