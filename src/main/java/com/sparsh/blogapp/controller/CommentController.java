package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.Comment;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import com.sparsh.blogapp.service.CommentService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable ObjectId commentId){
        try {
            Comment comment = commentService.getCommentById(commentId);

            return new ResponseEntity<>(comment , HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/save-comment/{id}")
    public ResponseEntity<?> saveComment(@RequestBody Comment inputComment , @PathVariable ObjectId id){

        if(inputComment.getCommentTitle() == null || inputComment.getCommentTitle().isBlank()){
            throw new NullPointerException("Title required for this request");
        }

        Comment savedComment = commentService.saveComment(inputComment , id);

        return new ResponseEntity<>(savedComment , HttpStatus.CREATED);
    }

}
