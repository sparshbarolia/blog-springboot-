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

    public BlogEntry saveBlogEntryWithDateUpdate(BlogEntry blogEntry){
        blogEntry.setDate(LocalDateTime.now());
        BlogEntry curr = blogEntryRepository.save(blogEntry);
        return curr;
    }

    public BlogEntry saveBlogEntryWithoutDateUpdate(BlogEntry blogEntry){
        BlogEntry curr = blogEntryRepository.save(blogEntry);
        return curr;
    }
}
