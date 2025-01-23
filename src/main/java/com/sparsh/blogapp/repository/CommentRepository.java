package com.sparsh.blogapp.repository;

import com.sparsh.blogapp.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
    Comment getCommentByCommentId(ObjectId id);
}