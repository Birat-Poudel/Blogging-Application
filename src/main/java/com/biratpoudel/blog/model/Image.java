package com.biratpoudel.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @NotEmpty(message = "Image URL cannot be empty or null!")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    @NotNull(message = "Post cannot be empty or null!")
    private Post post;

    public Image(String imageUrl, Post post) {
        this.imageUrl = imageUrl;
        this.post = post;
    }
}
