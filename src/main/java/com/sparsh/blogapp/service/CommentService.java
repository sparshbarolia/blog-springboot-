package com.sparsh.blogapp.service;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.Comment;
import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import com.sparsh.blogapp.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @Autowired
    private BlogEntryService blogEntryService;

    @Autowired
    private UserService userService;

    public Comment getCommentById(ObjectId commentId){
        return commentRepository.getCommentByCommentId(commentId);
    }

    @Transactional
    public Comment saveComment(Comment inputComment , ObjectId blogId,String userName){
        try {
            //get the blog
            BlogEntry currBlogEntry = blogEntryRepository.findByBlogId(blogId);
            //get the user
            User currUser = userService.findUserByUserName(userName);

            if(currBlogEntry == null || currUser == null){
                throw new RuntimeException("Please enter valid blogId and userName");
            }

            //save the comment
            inputComment.setDate(LocalDateTime.now());
            inputComment.setCommenter(currUser);
            inputComment.setParentBlog(currBlogEntry);
            Comment savedComment = commentRepository.save(inputComment);

            return savedComment;
        }
        catch (Exception e){
//            log.error(String.valueOf(e));
            throw new RuntimeException("An error occurred while saving the comment.",e);
        }
    }


    public Comment updateCommentOfUser(ObjectId commentId,String userName , Comment inputComment){
        try {
            Comment currComment = commentRepository.getCommentByCommentId(commentId);

            if(currComment == null){
                throw new RuntimeException("Please enter valid commentId");
            }

            //comment doesn't belong to user
            if(!currComment.getCommenter().getUserName().equals(userName)){
                throw new RuntimeException("Comment doesn't belong to the given user");
            }

            //updating comment
            if(inputComment.getCommentTitle() != null && !inputComment.getCommentTitle().isBlank())  currComment.setCommentTitle(inputComment.getCommentTitle());
            if(inputComment.getCommentContent() != null && !inputComment.getCommentContent().isBlank()) currComment.setCommentContent(inputComment.getCommentContent());

            return commentRepository.save(currComment);

        }
        catch (Exception e){
            throw new RuntimeException("Error in updating comment.",e);
        }


    }

    @Transactional
    public boolean commentDeletionByUser(ObjectId commentId,String userName){
        boolean removed = false;
        try {
            Comment currComment = commentRepository.getCommentByCommentId(commentId);

            if(currComment == null){
                throw new RuntimeException("No such comment exists");
            }

            if(!currComment.getCommenter().getUserName().equals(userName)){
                throw new RuntimeException("Comment doesn't belong to the user");
            }

            return (commentRepository.deleteCommentByCommentId(commentId) == 1);
        }
        catch (Exception e){
            throw new RuntimeException("Error in deleting Comment.",e);
        }
    }

    public long deleteCommentsByCommenterName(String userName){
        return commentRepository.deleteByCommenterUserName(userName);
    }

    public long deleteCommentsByBlogId(ObjectId blogId){
        return commentRepository.deleteByParentBlogId(blogId);
    }

}
