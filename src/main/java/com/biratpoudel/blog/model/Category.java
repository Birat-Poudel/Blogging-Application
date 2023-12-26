package com.biratpoudel.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @NotNull(message = "Category Title cannot be null or empty!")
    @Size(max = 100, message = "Category Title cannot be more than 100 characters!")
    private String categoryTitle;

    @NotNull(message = "Category Description cannot be null or empty!")
    @Column(columnDefinition = "TEXT")
    private String categoryDescription;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;
}
