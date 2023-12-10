package com.biratpoudel.blog.dto;

import com.biratpoudel.blog.model.Comment;
import com.biratpoudel.blog.model.Image;
import com.biratpoudel.blog.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


import java.util.Date;
import java.util.List;

public record PostDto(

        Long postId,

        @NotEmpty(message = "Title cannot be null or empty!")
        String title,

        @NotEmpty(message = "Description cannot be null or empty!")
        String description,

//        @NotEmpty(message = "Main Image cannot be null or empty!")
        String mainImage,

//        @NotNull(message = "Likes cannot be null!")
        Integer likes,

//        @NotNull(message = "Dislikes cannot be null!")
        Integer dislikes,

        @NotNull(message = "Date Created At cannot be null!")
        Date createdAt,

        @NotNull(message = "Date Updated At cannot be null!")
        Date updatedAt,

//        @NotNull(message = "Sub Images cannot be null!")
        List<Image> subImages,

//        @Valid
//        @NotNull(message = "User cannot be null!")
//        @JsonIgnore
        UserResponse user,

//        @Valid
//        @JsonIgnore
        List<Comment> comments
) {
}
