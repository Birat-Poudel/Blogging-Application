package com.biratpoudel.blog.dto;

import com.biratpoudel.blog.model.Category;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PostRequest(
        @NotNull(message = "Title cannot be null or empty!")
        String title,

        @NotNull(message = "Description cannot be null or empty!")
        String description,

        @NotNull(message = "Category cannot be null or empty!")
        List<Category> categories
) {
}
