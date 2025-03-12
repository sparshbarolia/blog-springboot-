package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.Comment;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import com.sparsh.blogapp.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @GetMapping("/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable ObjectId commentId){
        try {
            Comment comment = commentService.getCommentById(commentId);

            if(comment == null){
                throw new RuntimeException("No such comment exists");
            }

            return new ResponseEntity<>(comment , HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("No such comment exists",HttpStatus.NOT_FOUND);
        }
    }

    //koi bhi user,kisi bhi blog pr comment kr skta h
    @PostMapping("/{blogId}")
    public ResponseEntity<?> saveComment(
            @RequestBody Comment inputComment ,
            @PathVariable ObjectId blogId
    ){

        if(inputComment.getCommentTitle() == null || inputComment.getCommentTitle().isBlank()){
            throw new NullPointerException("Title required for this request");
        }

        //is line ka mtlb h ki user verify hogya ki usne sahi userName and password dala h
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //ye us verified user ka userName ladega
        String currUserName = authentication.getName();

        Comment savedComment = commentService.saveComment(inputComment , blogId , currUserName);

        return new ResponseEntity<>(savedComment , HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable ObjectId commentId,
            @RequestBody Comment inputComment
    ){
        try {
            //is line ka mtlb h ki user verify hogya ki usne sahi userName and password dala h
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //ye us verified user ka userName ladega
            String currUserName = authentication.getName();

            Comment updatedComment = commentService.updateCommentOfUser(commentId,currUserName,inputComment);
            return new ResponseEntity<>(updatedComment,HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Error in updating comment",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable ObjectId commentId){
        try {
            //is line ka mtlb h ki user verify hogya ki usne sahi userName and password dala h
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //ye us verified user ka userName ladega
            String currUserName = authentication.getName();

            boolean flag = commentService.commentDeletionByUser(commentId,currUserName);

            if(!flag) throw new RuntimeException("No such comment exists for current user.");

            return new ResponseEntity<>(flag,HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            log.error("Error in deleting comment.",e);
            return new ResponseEntity<>("Error in deleting comment.",HttpStatus.NOT_FOUND);
        }
    }

}
