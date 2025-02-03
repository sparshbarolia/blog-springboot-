package com.sparsh.blogapp.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection="comments")
@NoArgsConstructor
public class Comment {
    @Id
    private ObjectId commentId;

    @NonNull
    private String commentTitle;

    private String commentContent;

    private LocalDateTime date;

    @DBRef
    private User commenter;

    @DBRef
    private BlogEntry parentBlog;

}
