package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.service.BlogEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogEntryController {

    @Autowired
    private BlogEntryService blogEntryService;


    @PostMapping("/save-blog")
    public ResponseEntity<?> saveNewBlog(@RequestBody BlogEntry blogEntry){
        BlogEntry currSavedEntry = blogEntryService.saveBlogEntry(blogEntry);

        return new ResponseEntity<>(currSavedEntry , HttpStatus.CREATED);
    }
}
