package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.Category;
import com.sparsh.blogapp.entity.Comment;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import com.sparsh.blogapp.repository.CommentRepository;
import com.sparsh.blogapp.service.BlogEntryService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog")
@Slf4j
public class BlogEntryController {

    @Autowired
    private BlogEntryService blogEntryService;

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/{blogId}")
    public ResponseEntity<?> getBlogById(@PathVariable ObjectId blogId){
        try {
            BlogEntry blogEntry = blogEntryRepository.findByBlogId(blogId);

            if(blogEntry == null) throw new RuntimeException("No such blog entry found");

            return new ResponseEntity<>(blogEntry , HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("No such blog found.",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{blogId}/comments")
    public ResponseEntity<?> getAllCommentsOfBlog(@PathVariable ObjectId blogId){
        try {
            BlogEntry blogEntry = blogEntryRepository.findByBlogId(blogId);
//            List<Comment> comments = commentRepository.findByParentBlog_BlogId(blogId);
            List<Comment> comments = commentRepository.findByParentBlogId(blogId);

            if(comments.isEmpty()){
                throw new RuntimeException("No comments exist for current provided blog");
            }

            return new ResponseEntity<>(comments.stream()
                                            .sorted(Comparator.comparing(Comment::getDate)) // Sorts by the `date` field in ascending order
//                                            .sorted(Comparator.comparing(Comment::getDate).reversed()) // Sorts by the `date` field in descending order
                                            .collect(Collectors.toList()) , HttpStatus.OK);
        }
        catch (Exception e){
            log.error("No such blog found.",e);
            return new ResponseEntity<>("No such blog found.",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/user/{inputUserName}/category/{inputCategoryName}")
    public ResponseEntity<?> saveNewBlog(
            @RequestBody BlogEntry blogEntry,
            @PathVariable String inputCategoryName,
            @PathVariable String inputUserName
    ){
        try {
            if(blogEntry.getTitle() == null || blogEntry.getTitle().isBlank()){
                throw new NullPointerException("Title required in input");
            }
            BlogEntry currSavedEntry = blogEntryService.saveBlogEntryWithDateUpdate(blogEntry,inputCategoryName,inputUserName);

            return new ResponseEntity<>(currSavedEntry , HttpStatus.CREATED);
        }
        catch (Exception e){
            log.error("Error in creating the blog",e);
            return new ResponseEntity<>("Blog not created.",HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{blogId}/user/{userName}")
    public ResponseEntity<?> deleteBlog(@PathVariable ObjectId blogId, @PathVariable String userName){
        try {
            boolean isDeleted = false;

            isDeleted = blogEntryService.deleteBlog(blogId,userName);

            if(!isDeleted)throw new RuntimeException("Error in deleting blogEntry.");

            return new ResponseEntity<>(isDeleted,HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            log.error("error in deleting blog",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
