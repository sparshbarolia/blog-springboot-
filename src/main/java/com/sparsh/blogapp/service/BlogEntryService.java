package com.sparsh.blogapp.service;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.Category;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class BlogEntryService {

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    public BlogEntry saveBlogEntryWithDateUpdate(BlogEntry blogEntry, String inputCategoryName){
        try {
            //save blog
            blogEntry.setDate(LocalDateTime.now());
            BlogEntry curr = blogEntryRepository.save(blogEntry);

            //get category and push blog in categoryBlogs array
            Category fetchedCategory = categoryService.getCategoryByName(inputCategoryName);
            fetchedCategory.getCategoryBlogs().add(curr);

            //save that category
            categoryService.saveCategory(fetchedCategory);

            return curr;
        }
        catch (Exception e){
            throw new RuntimeException("An error occurred while saving the blog.",e);
        }
    }

    public BlogEntry saveBlogEntryWithoutDateUpdate(BlogEntry blogEntry){
        BlogEntry curr = blogEntryRepository.save(blogEntry);
        return curr;
    }
}
