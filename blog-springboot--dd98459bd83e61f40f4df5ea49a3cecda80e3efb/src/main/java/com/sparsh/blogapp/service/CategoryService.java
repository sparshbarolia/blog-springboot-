package com.sparsh.blogapp.service;

import com.sparsh.blogapp.entity.Category;
import com.sparsh.blogapp.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category inputCategory){
        return categoryRepository.save(inputCategory);
    }

    public Category getCategoryByName(String inputName){
        return categoryRepository.findByCategoryName(inputName);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public List<Category> getSortedCategoriesByName(){
        return categoryRepository.findAll(Sort.by(Sort.Direction.ASC,"categoryName"));
    }

}
