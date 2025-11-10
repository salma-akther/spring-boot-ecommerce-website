package com.ecommerce.sbproject.Servicce;

import com.ecommerce.sbproject.model.Category;
import com.ecommerce.sbproject.payload.CategoryDTO;
import com.ecommerce.sbproject.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
