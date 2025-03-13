package com.sparsh.blogapp.controller;

import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.service.UserDetailsServiceImpl;
import com.sparsh.blogapp.service.UserService;
import com.sparsh.blogapp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

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

    //jwt token generate krdega if userName and pass sahi hue to
    @PostMapping("/login")  //user creation public rehna chahiye
    public ResponseEntity<String> login(@RequestBody User newUser){
        try {
            //to authenticate user using UserDetailsServiceImplementaion and spring security
            //agr userName,password match nhi hua to yahi pr error ajaega and catch me chle jaenge
            //spring security me authenticationManagerBean() function bnaya h iske liye
            // /public end point secure nhi h isliye alg se verification krni pdi hume user ki yaha
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(newUser.getUserName() , newUser.getPassword()));
            //authenticated user ki details lakr dedo
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(newUser.getUserName());
            //generate token
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt , HttpStatus.OK);
        }
        catch (Exception e){
            log.error("exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect userName or password",HttpStatus.NOT_FOUND);
        }
    }

}

