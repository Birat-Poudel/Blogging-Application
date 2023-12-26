package com.biratpoudel.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotEmpty(message = "Title cannot be null or empty!")
    private String title;

    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "Description cannot be null or empty!")
    private String description;

    @OneToMany
    @JoinColumn(name = "category_id")
    //@Valid
    private List<Category> categories;

    //@NotEmpty(message = "Main Image cannot be null or empty!")
    private String mainImage;

    //@NotNull(message = "Likes cannot be null!")
    private Integer likes;

    //@NotNull(message = "Dislikes cannot be null!")
    private Integer dislikes;

    @NotNull(message = "Date Created At cannot be null!")
    private Date createdAt;

    @NotNull(message = "Date Updated At cannot be null!")
    private Date updatedAt;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    //@Valid
    private List<Image> subImages;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    //@NotNull(message = "User cannot be null!")
    //@Valid
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    //@Valid
    private List<Comment> comments;

    public Post(String title, String description, List<Category> categories, String mainImage, Integer likes, Integer dislikes, Date createdAt, Date updatedAt, List<Image> subImages, User user, List<Comment> comments) {
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.mainImage = mainImage;
        this.likes = likes;
        this.dislikes = dislikes;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.subImages = subImages;
        this.user = user;
        this.comments = comments;
    }

    public Post(String title, String description, List<Category> categories) {
        this.title = title;
        this.description = description;
        this.categories = categories;
    }
}
