package com.sparsh.blogapp.repository;

import com.sparsh.blogapp.entity.BlogEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface BlogEntryRepository extends MongoRepository<BlogEntry, ObjectId> {

    BlogEntry findByBlogId(ObjectId id);

    long deleteBlogByBlogId(ObjectId id);
}