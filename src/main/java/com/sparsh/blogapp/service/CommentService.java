package com.sparsh.blogapp.service;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.Comment;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import com.sparsh.blogapp.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @Autowired
    private BlogEntryService blogEntryService;

    public Comment getCommentById(ObjectId commentId){
        return commentRepository.getCommentByCommentId(commentId);
    }

    @Transactional
    public Comment saveComment(Comment inputComment , ObjectId id){
        try {
            //save the comment
            inputComment.setDate(LocalDateTime.now());
            Comment savedComment = commentRepository.save(inputComment);

            //search for blog
            BlogEntry blogEntry = blogEntryRepository.findByBlogId(id);

            //add comment in blog and save blog
            blogEntry.getBlogComments().add(savedComment);
            blogEntryService.saveBlogEntryWithoutDateUpdate(blogEntry);

            return savedComment;
        }
        catch (Exception e){
//            log.error(String.valueOf(e));
            throw new RuntimeException("An error occurred while saving the comment.",e);
        }
    }

}
