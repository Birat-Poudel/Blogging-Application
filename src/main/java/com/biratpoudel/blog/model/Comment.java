package com.biratpoudel.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(columnDefinition = "TEXT")
    @NotEmpty(message = "Comment cannot be null or empty!")
    private String comment;

    @NotNull(message = "Likes cannot be null!")
    private Integer likes;

    @NotNull(message = "DisLikes cannot be null!")
    private Integer dislikes;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
