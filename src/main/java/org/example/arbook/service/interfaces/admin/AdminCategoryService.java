package org.example.arbook.service.interfaces.admin;

import org.example.arbook.model.dto.request.CategoryReq;
import org.example.arbook.model.dto.request.CategoryUpdateReq;
import org.example.arbook.model.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AdminCategoryService {

    Category getCategory(Long id);

    List<Category> getCategories();

    Category addCategory(CategoryReq categoryReq);

    void updateCategory(Long categoryId, CategoryUpdateReq categoryUpdateReq);
}