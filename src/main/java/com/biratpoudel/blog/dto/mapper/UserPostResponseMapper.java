package com.biratpoudel.blog.dto.mapper;

import com.biratpoudel.blog.dto.PostUserResponse;
import com.biratpoudel.blog.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserPostResponseMapper implements Function<User, PostUserResponse> {

    @Override
    public PostUserResponse apply(User user) {
        return new PostUserResponse(
                user.getUserId(),
                user.getUsername()
        );
    }
}
