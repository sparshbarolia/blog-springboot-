package com.sparsh.blogapp.service;

import com.sparsh.blogapp.entity.BlogEntry;
import com.sparsh.blogapp.repository.BlogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BlogEntryService {

    @Autowired
    private BlogEntryRepository blogEntryRepository;

    public BlogEntry saveBlogEntry(BlogEntry blogEntry){

        blogEntry.setDate(LocalDateTime.now());
        BlogEntry curr = blogEntryRepository.save(blogEntry);

        return curr;
    }
}
