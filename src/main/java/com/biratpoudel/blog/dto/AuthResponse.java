package com.biratpoudel.blog.dto;

public record AuthResponse(
        String token,
        String message
) {
}
