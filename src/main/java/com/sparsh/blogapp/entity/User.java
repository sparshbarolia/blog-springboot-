package com.sparsh.blogapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "users")
@NoArgsConstructor
public class User {
    @Id
    private ObjectId userId;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    @NonNull
    private String password;

//    @DBRef
//    private List<BlogEntry> blogEntries = new ArrayList<>();

    private List<String> roles;
}
