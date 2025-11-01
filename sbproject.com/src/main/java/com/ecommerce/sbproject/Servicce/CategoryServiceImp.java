package com.ecommerce.sbproject.Servicce;

import com.ecommerce.sbproject.exceptions.APIException;
import com.ecommerce.sbproject.exceptions.ResourceNotFoundException;
import com.ecommerce.sbproject.model.Category;
import com.ecommerce.sbproject.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new APIException("No category created till now");        }
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory!= null){
            throw new APIException(("Category with the name " + category.getCategoryName() + " already exist !!!"));
        }
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return "Category with categoryId: " + categoryId + " deleted successfully !!";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return savedCategory;
    }
}
