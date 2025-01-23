package com.sparsh.blogapp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//for lombok
@Data
@Document(collection="blog_entries")
//Without @NoArgsConstructor: If you define no constructor explicitly,
//and the class has fields like @NonNull, Lombok will not generate any constructor automatically.
@NoArgsConstructor
public class BlogEntry {
    @Id
    private ObjectId blogId;

    @NonNull
    private String title;

    private String content;

    private LocalDateTime date;

    @DBRef
    private List<Comment> blogComments = new ArrayList<>();

}
