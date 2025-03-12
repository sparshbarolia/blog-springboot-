package com.sparsh.blogapp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @GetMapping("/health-check")
    public ResponseEntity<String> getHealthCheck(){
        return new ResponseEntity<>("HEALTH OK", HttpStatus.OK);
    }

}

