package com.biratpoudel.blog.controller;

import com.biratpoudel.blog.dto.UserRequest;
import com.biratpoudel.blog.dto.UserResponse;
import com.biratpoudel.blog.model.User;
import com.biratpoudel.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponse> updateUserById(@RequestHeader("Authorization") String jwt, @RequestBody UserRequest userRequest) {
        User user = userService.findUserByToken(jwt);
        return ResponseEntity.ok(userService.update(user.getUserId(), userRequest));
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteUserById(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByToken(jwt);
        userService.deleteById(user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/profile")
    public User getUserByToken(@RequestHeader("Authorization") String jwt) {
        return userService.findUserByToken(jwt);
    }
}
