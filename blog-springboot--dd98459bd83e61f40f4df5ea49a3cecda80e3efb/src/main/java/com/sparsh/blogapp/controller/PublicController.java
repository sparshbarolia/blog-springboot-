package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public ResponseEntity<String> getHealthCheck(){
        return new ResponseEntity<>("HEALTH OK", HttpStatus.OK);
    }


    @PostMapping("/create-user")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        try {
            if(user.getUserName()==null || user.getPassword()==null || user.getUserName().isBlank() || user.getPassword().isBlank()){
                throw new NullPointerException("Please enter mandatory fields of user.");
            }
            return new ResponseEntity<>(userService.saveUserAndSetRole(user), HttpStatus.CREATED);
        }
        catch (Exception e){
            log.error("Error in creating the user.",e);
            return new ResponseEntity<>("User not created.",HttpStatus.BAD_REQUEST);
        }
    }

}

