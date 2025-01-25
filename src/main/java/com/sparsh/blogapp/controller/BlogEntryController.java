package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.Category;
import com.sparsh.blogapp.entity.Comment;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import com.sparsh.blogapp.service.BlogEntryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogEntryController {

    @Autowired
    private BlogEntryService blogEntryService;

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @GetMapping("/{blogId}")
    public ResponseEntity<?> getBlogById(@PathVariable ObjectId blogId){
        try {
            BlogEntry blogEntry = blogEntryRepository.findByBlogId(blogId);

            return new ResponseEntity<>(blogEntry , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("No such blog found.",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/blog-comments/{blogId}")
    public ResponseEntity<?> getAllCommentsOfBlog(@PathVariable ObjectId blogId){
        try {
            BlogEntry blogEntry = blogEntryRepository.findByBlogId(blogId);
            List<Comment> comments = blogEntry.getBlogComments();

            return new ResponseEntity<>(comments.stream()
                                            .sorted(Comparator.comparing(Comment::getDate)) // Sorts by the `date` field in ascending order
//                                            .sorted(Comparator.comparing(Comment::getDate).reversed()) // Sorts by the `date` field in descending order
                                            .collect(Collectors.toList()) , HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("No such blog found.",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/category/{inputCategoryName}")
    public ResponseEntity<?> saveNewBlog(@RequestBody BlogEntry blogEntry, @PathVariable String inputCategoryName){
        if(blogEntry.getTitle() == null || blogEntry.getTitle().isBlank()){
            throw new NullPointerException("Title required for this request");
        }
        BlogEntry currSavedEntry = blogEntryService.saveBlogEntryWithDateUpdate(blogEntry,inputCategoryName);

        return new ResponseEntity<>(currSavedEntry , HttpStatus.CREATED);
    }
}
