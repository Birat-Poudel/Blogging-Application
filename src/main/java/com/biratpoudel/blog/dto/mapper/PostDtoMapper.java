package com.biratpoudel.blog.dto.mapper;

import com.biratpoudel.blog.dto.PostDto;
import com.biratpoudel.blog.model.Post;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PostDtoMapper implements Function<Post, PostDto> {

    private final UserPostResponseMapper userPostResponseMapper;

    public PostDtoMapper(UserPostResponseMapper userPostResponseMapper) {
        this.userPostResponseMapper = userPostResponseMapper;
    }

    @Override
    public PostDto apply(Post post) {
        return new PostDto(
                post.getPostId(),
                post.getTitle(),
                post.getDescription(),
                post.getMainImage(),
                post.getLikes(),
                post.getDislikes(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getSubImages(),
                userPostResponseMapper.apply(post.getUser()),
                post.getComments()
        );
    }
}
