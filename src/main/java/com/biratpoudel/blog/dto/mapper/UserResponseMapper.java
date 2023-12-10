package com.biratpoudel.blog.dto.mapper;

import com.biratpoudel.blog.dto.UserResponse;
import com.biratpoudel.blog.model.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserResponseMapper implements Function<User, UserResponse> {

    @Override
    public UserResponse apply(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getPosts(),
                user.getComments()
        );
    }
}
