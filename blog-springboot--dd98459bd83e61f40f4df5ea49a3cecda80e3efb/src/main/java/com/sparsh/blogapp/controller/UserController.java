package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.repository.UserRepository;
import com.sparsh.blogapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

//    @PostMapping
//    public ResponseEntity<?> saveUser(@RequestBody User user){
//        try {
//            if(user.getUserName()==null || user.getPassword()==null || user.getUserName().isBlank() || user.getPassword().isBlank()){
//                throw new NullPointerException("Please enter mandatory fields of user.");
//            }
//            return new ResponseEntity<>(userService.saveUserAndSetRole(user), HttpStatus.CREATED);
//        }
//        catch (Exception e){
//            log.error("Error in creating the user.",e);
//            return new ResponseEntity<>("User not created.",HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        try {
            return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Error in fetching all users.",e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
