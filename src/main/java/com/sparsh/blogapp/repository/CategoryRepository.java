package com.sparsh.blogapp.repository;

import com.sparsh.blogapp.entity.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CategoryRepository extends MongoRepository<Category, ObjectId> {
    Category findByCategoryName(String categoryName);
}