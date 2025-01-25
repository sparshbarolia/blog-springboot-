package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.Category;
import com.sparsh.blogapp.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{inputCategoryName}")
    public ResponseEntity<?> getCategoryByCategoryName(@PathVariable String inputCategoryName){
        try {
            return new ResponseEntity<>(categoryService.getCategoryByName(inputCategoryName) , HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list-all")
    public ResponseEntity<?> getAllCategories(){
        try {
            return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody Category inputCategory){
        try {
            Category savedCategory = categoryService.saveCategory(inputCategory);

            return new ResponseEntity<>(savedCategory , HttpStatus.CREATED);
        }
        catch (Exception e){
            log.error("agye yaha",e);
            return new ResponseEntity<>("Category not created",HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/sort-asc")
    public ResponseEntity<?> getAscSortedCategoriesByName(){
        try {
            return new ResponseEntity<>(categoryService.getSortedCategoriesByName() , HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
