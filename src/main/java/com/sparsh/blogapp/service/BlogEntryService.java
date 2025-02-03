package com.sparsh.blogapp.service;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.Category;
import com.sparsh.blogapp.entity.Comment;
import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import com.sparsh.blogapp.repository.CommentRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BlogEntryService {

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public BlogEntry saveBlogEntryWithDateUpdate(BlogEntry blogEntry, String inputCategoryName,String inputUserName){
        try {
            //fetch category and user
            Category fetchedCategory = categoryService.getCategoryByName(inputCategoryName);
            User fetchedUser = userService.findUserByUserName(inputUserName);

            if(fetchedCategory == null || fetchedUser == null){
                throw new RuntimeException("Please enter valid userName and category");
            }

            //save blog
            blogEntry.setDate(LocalDateTime.now());
            blogEntry.setCategory(fetchedCategory);
            blogEntry.setAuthor(fetchedUser);
            BlogEntry curr = blogEntryRepository.save(blogEntry);

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

    @Transactional
    public boolean deleteBlog(ObjectId blogId,String userName){
        try {
            //get curr blog
            BlogEntry currBlog = blogEntryRepository.findByBlogId(blogId);

            if(currBlog == null){
                throw new RuntimeException("Please enter valid blogId");
            }

            //blog doesn't belong to user
            if(!currBlog.getAuthor().getUserName().equals(userName)){
                throw new RuntimeException("No such blog exists for current user.");
            }

            long deletedCommentsNum = commentRepository.deleteByParentBlogId(blogId);

            return (blogEntryRepository.deleteBlogByBlogId(blogId) == 1);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
