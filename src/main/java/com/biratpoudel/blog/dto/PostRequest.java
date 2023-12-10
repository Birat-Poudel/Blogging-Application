package com.biratpoudel.blog.dto;

import jakarta.validation.constraints.NotEmpty;

public record PostRequest(
        @NotEmpty(message = "Title cannot be null or empty!")
        String title,

        @NotEmpty(message = "Description cannot be null or empty!")
        String description
) {
}
