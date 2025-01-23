package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.Comment;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import com.sparsh.blogapp.service.BlogEntryService;
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save-blog")
    public ResponseEntity<?> saveNewBlog(@RequestBody BlogEntry blogEntry){
        BlogEntry currSavedEntry = blogEntryService.saveBlogEntryWithDateUpdate(blogEntry);

        return new ResponseEntity<>(currSavedEntry , HttpStatus.CREATED);
    }
}
