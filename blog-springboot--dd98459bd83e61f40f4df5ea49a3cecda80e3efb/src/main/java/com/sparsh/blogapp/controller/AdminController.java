package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    //Admin can create an admin
    @PostMapping("/create-admin")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        try {
            if(user.getUserName()==null || user.getPassword()==null || user.getUserName().isBlank() || user.getPassword().isBlank()){
                throw new NullPointerException("Please enter mandatory fields of user.");
            }
            return new ResponseEntity<>(adminService.saveAdminAndSetRole(user), HttpStatus.CREATED);
        }
        catch (Exception e){
            log.error("Error in creating the user.",e);
            return new ResponseEntity<>("User not created.",HttpStatus.BAD_REQUEST);
        }
    }

    //Admin can delete any blog
    @DeleteMapping("/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable ObjectId blogId){
        try {
            //is line ka mtlb h ki user verify hogya ki usne sahi userName and password dala h
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //ye us verified user ka userName ladega
            String currUserName = authentication.getName();

            boolean isDeleted = false;

            isDeleted = adminService.deleteBlog(blogId);

            if(!isDeleted)throw new RuntimeException("Error in deleting blogEntry.");

            return new ResponseEntity<>(isDeleted, HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            log.error("error in deleting blog",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
