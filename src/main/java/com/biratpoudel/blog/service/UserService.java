package com.biratpoudel.blog.service;

import com.biratpoudel.blog.dto.AuthResponse;
import com.biratpoudel.blog.dto.UserRequest;
import com.biratpoudel.blog.dto.UserResponse;
import com.biratpoudel.blog.model.User;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(Long id);

    AuthResponse register(UserRequest userRequest) throws Exception;
    AuthResponse login(UserRequest userRequest);

    UserResponse update(Long userId, UserRequest userRequest);
    void deleteById(Long userId);
    User findUserByToken(String jwt);
}
