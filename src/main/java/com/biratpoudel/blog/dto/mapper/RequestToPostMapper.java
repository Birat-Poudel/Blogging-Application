package com.biratpoudel.blog.dto.mapper;

import com.biratpoudel.blog.dto.PostRequest;
import com.biratpoudel.blog.model.Post;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class RequestToPostMapper implements Function<PostRequest, Post> {

    @Override
    public Post apply(PostRequest postRequest) {
        return new Post(
                postRequest.title(),
                postRequest.description(),
                postRequest.categories()
        );
    }
}
