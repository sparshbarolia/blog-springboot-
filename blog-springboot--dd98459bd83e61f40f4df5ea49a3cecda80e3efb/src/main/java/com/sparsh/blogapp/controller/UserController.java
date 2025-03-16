package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.repository.UserRepository;
import com.sparsh.blogapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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

    @PostMapping("/update")
    public ResponseEntity<?> changeUserNameOrPassword(@RequestBody User inputUpdatedUser){
        try {
            //is line ka mtlb h ki user verify hogya ki usne sahi userName and password dala h
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //ye us verified user ka userName ladega
            String currUserName = authentication.getName();

            User currUser = userService.findUserByUserName(currUserName);

            //ye 3 conditions satisfy hogyi mtlb userName update krna pdega
            if (inputUpdatedUser.getUserName() != null
                    && !inputUpdatedUser.getUserName().isBlank()
                    && !inputUpdatedUser.getUserName().equals(currUserName))
            {
                User findUserWithSameName = userService.findUserByUserName(inputUpdatedUser.getUserName());

                if(findUserWithSameName != null) throw new RuntimeException("Please enter unique userName!");

                currUser.setUserName(inputUpdatedUser.getUserName());
            }

            if(inputUpdatedUser.getPassword() != null && !inputUpdatedUser.getPassword().isBlank()){
                currUser.setPassword(passwordEncoder.encode(inputUpdatedUser.getPassword()));
            }

            userService.saveUser(currUser);

            return new ResponseEntity<>(currUser,HttpStatus.OK);
        }
        catch (Exception e){
            log.error("Error in updating userName and password",e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


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
