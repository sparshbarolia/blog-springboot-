package com.sparsh.blogapp.repository;

import com.sparsh.blogapp.entity.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {
    Comment getCommentByCommentId(ObjectId id);

//    MongoDB’s delete operation returns the number of deleted documents, not true/false.
    long deleteCommentByCommentId(ObjectId id);

    // ✅ Deletes comments by userName
    @Query(value = "{ 'commenter.userName' : ?0 }", delete = true)
    long deleteByCommenterUserName(String userName);

    // ✅ Deletes comments by blogId
    @Query(value = "{ 'parentBlog.$id' : ?0 }", delete = true)
    long deleteByParentBlogId(ObjectId blogId);

//    // Query to find all comments by parentBlog's blogId
//    List<Comment> findByParentBlog_BlogId(ObjectId blogId);
//                         OR
    @Query(value = "{ 'parentBlog.$id' : ?0 }")
    List<Comment> findByParentBlogId(ObjectId blogId);
}