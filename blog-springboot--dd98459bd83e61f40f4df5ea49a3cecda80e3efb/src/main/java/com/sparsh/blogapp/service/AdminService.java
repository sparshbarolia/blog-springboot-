package com.sparsh.blogapp.service;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import com.sparsh.blogapp.repository.CommentRepository;
import com.sparsh.blogapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Component
public class AdminService {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public User saveAdminAndSetRole(User inputUser){
        try {
            inputUser.setPassword(passwordEncoder.encode(inputUser.getPassword()));
            inputUser.setRoles(Arrays.asList("USER","ADMIN"));
            return userRepository.save(inputUser);
        }
        catch (Exception e){
            throw new RuntimeException("Error in creating the user.",e);
        }
    }

    @Transactional
    public boolean deleteBlog(ObjectId blogId){
        try {
            //get curr blog
            BlogEntry currBlog = blogEntryRepository.findByBlogId(blogId);

            if(currBlog == null) throw new RuntimeException("Please enter valid blogId");

            long deletedCommentsNum = commentRepository.deleteByParentBlogId(blogId);

            return (blogEntryRepository.deleteBlogByBlogId(blogId) == 1);
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
