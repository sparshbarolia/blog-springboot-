package com.sparsh.blogapp.service;

import com.sparsh.blogapp.entity.User;
import com.sparsh.blogapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByUserName(username);
//        if (user != null) {
//            return org.springframework.security.core.userdetails.User.builder()
//                    .username(user.getUserName())  // Map your entity's `userName` to Spring Security's username
//                    .password(user.getPassword())
//                    .roles(user.getRoles().toArray(new String[0]))  //string type ki array me covert krdia List<String>  ko
//                    .build();
//        }
//        throw new UsernameNotFoundException("User not found with username: " + username);
//    }
//}

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    // Constructor injection instead of @Autowired on the field
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch the user from the repository
        User user = userRepository.findByUserName(username);

        // If the user is not found, throw an exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        // Build and return the UserDetails object
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserName())  // Map your entity's `userName` to Spring Security's username
                .password(user.getPassword())  // Use the encoded password from the database
                .roles(user.getRoles().toArray(new String[0]))  // Convert List<String> to String array for roles
                .build();
    }
}
