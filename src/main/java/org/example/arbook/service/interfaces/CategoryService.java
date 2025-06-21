package org.example.arbook.service.interfaces;

import org.example.arbook.model.dto.response.CategoryRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<CategoryRes> getActiveCategories();
}
