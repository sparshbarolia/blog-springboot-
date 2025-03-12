package com.sparsh.blogapp.config;

import com.sparsh.blogapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//@EnableWebSecurity
//public class SpringSecurity {
//
////    @Autowired
////    private UserDetailsServiceImpl userDetailsService;
//
//    //did this to prevent circular reference
//    private final UserDetailsService userDetailsService;
//
//    public SpringSecurity(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
////    @Autowired
////    private JwtFilter jwtFilter;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http.authorizeHttpRequests(request -> request
//                        .requestMatchers("/public/**", "/user/**").permitAll()
//                        .requestMatchers("/blog/**", "/category/**", "/comment/**").authenticated()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .anyRequest().authenticated())
////                .csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf.disable())
////                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
//        return auth.getAuthenticationManager();
//    }
//
//}

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    private final UserDetailsService userDetailsService;
//    private final JwtFilter jwtFilter;

//    // Constructor injection to avoid circular dependency
//    public SpringSecurity(UserDetailsService userDetailsService, JwtFilter jwtFilter) {
//        this.userDetailsService = userDetailsService;
//        this.jwtFilter = jwtFilter;
//    }

    // Constructor injection to avoid circular dependency
    public SpringSecurity(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/public/**", "/user/**").permitAll()
                        .requestMatchers("/blog/**", "/category/**", "/comment/**").authenticated() // Secure these endpoints
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Only ADMIN role can access these endpoints
//                        .anyRequest().permitAll() // Allow all other requests
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
                )
                .httpBasic(Customizer.withDefaults()) // Enable Basic Authentication
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
//                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter
                ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager(); // Expose AuthenticationManager as a bean
    }
}