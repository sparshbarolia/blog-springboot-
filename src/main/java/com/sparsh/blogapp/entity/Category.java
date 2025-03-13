package com.sparsh.blogapp.entity;

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
@Document(collection="categories")
@NoArgsConstructor
public class Category {
    @Id
    private ObjectId categoryId;

    //@Indexed ke liye application properties me ek line add krni pdti h
    //iski help se searching along userName becomes faster
    //also same userName ki 2 entries ni hone dega ye
    @Indexed(unique = true)
    @NonNull
    private String categoryName;

    private String categoryDescription;

//    @DBRef
//    private List<BlogEntry> categoryBlogs = new ArrayList<>();
}
