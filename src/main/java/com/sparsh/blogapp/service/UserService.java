package com.sparsh.blogapp.service;

import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUserAndSetRole(User inputUser){
        try {
            inputUser.setRoles(Arrays.asList("USER"));
            return userRepository.save(inputUser);
        }
        catch (Exception e){
            throw new RuntimeException("Error in creating the user.",e);
        }
    }

    public User saveUser(User inputUser){
        try {
            return userRepository.save(inputUser);
        }
        catch (Exception e){
            throw new RuntimeException("Error in creating the user.",e);
        }
    }



    public User findUserByUserName(String inputUserName){
        return userRepository.findByUserName(inputUserName);
    }
}
