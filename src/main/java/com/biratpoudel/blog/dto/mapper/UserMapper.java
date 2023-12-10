package com.biratpoudel.blog.dto.mapper;

import com.biratpoudel.blog.dto.UserRequest;
import com.biratpoudel.blog.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserMapper implements Function<UserRequest, User> {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User apply(UserRequest userRequest) {
        return new User(
                userRequest.username(),
                passwordEncoder.encode(userRequest.password())
        );
    }
}
