package com.biratpoudel.blog.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(columnDefinition = "TEXT")
    @NotNull(message = "Reply text cannot be null or empty!")
    private String replyText;

    @NotNull(message = "Likes cannot be null!")
    private Integer likes;

    @NotNull(message = "DisLikes cannot be null!")
    private Integer dislikes;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    @NotNull(message = "Comment cannot be null!")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "parent_reply_id")
    private Reply parentReply;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
