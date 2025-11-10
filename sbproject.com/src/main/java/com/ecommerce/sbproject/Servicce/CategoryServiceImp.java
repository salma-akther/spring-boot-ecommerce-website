package com.ecommerce.sbproject.Servicce;

import com.ecommerce.sbproject.exceptions.APIException;
import com.ecommerce.sbproject.exceptions.ResourceNotFoundException;
import com.ecommerce.sbproject.model.Category;
import com.ecommerce.sbproject.payload.CategoryDTO;
import com.ecommerce.sbproject.payload.CategoryResponse;
import com.ecommerce.sbproject.repositories.CategoryRepository;
import jakarta.persistence.Entity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CategoryServiceImp implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new APIException("No category created till now");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category-> modelMapper.map(category, CategoryDTO.class))
                .toList();
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {


        Category categoryFromDb = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (categoryFromDb!= null){
            throw new APIException(("Category with the name " + categoryDTO.getCategoryName() + " already exist !!!"));
        }
        //Entity to DTO
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        //DTO to Entity
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory (CategoryDTO categoryDTO, Long categoryId){

        Category categoryFromDB = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryDTO.setCategoryId(categoryId);
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
