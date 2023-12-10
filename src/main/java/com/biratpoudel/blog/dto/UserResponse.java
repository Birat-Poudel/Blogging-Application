package com.biratpoudel.blog.dto;

import com.biratpoudel.blog.model.Comment;
import com.biratpoudel.blog.model.Post;

import java.util.List;

public record UserResponse(
        Long userId,
        String username,
        List<Post> posts,
        List<Comment> comments

) {
}
