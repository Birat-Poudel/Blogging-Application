package com.biratpoudel.blog.dto;

import com.biratpoudel.blog.model.Category;
import com.biratpoudel.blog.model.Comment;
import com.biratpoudel.blog.model.Image;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public record PostDto(

        Long postId,

        @NotNull(message = "Title cannot be null or empty!")
        String title,

        @NotNull(message = "Description cannot be null or empty!")
        String description,

        @NotNull(message = "Category cannot be null or empty!")
        List<Category> categories,

        String mainImage,

        Integer likes,

        Integer dislikes,

        @NotNull(message = "Date Created At cannot be null!")
        Date createdAt,

        @NotNull(message = "Date Updated At cannot be null!")
        Date updatedAt,

        List<Image> subImages,

        PostUserResponse user,

        List<Comment> comments
) {
}
