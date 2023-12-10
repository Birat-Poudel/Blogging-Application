package com.biratpoudel.blog.controller;

import com.biratpoudel.blog.config.AppConstants;
import com.biratpoudel.blog.dto.PostDto;
import com.biratpoudel.blog.dto.PostRequest;
import com.biratpoudel.blog.dto.PostResponse;
import com.biratpoudel.blog.model.User;
import com.biratpoudel.blog.service.PostService;
import com.biratpoudel.blog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostResponse posts = postService.findAll(pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }

    @PostMapping(value = "/posts", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<PostDto> savePostById(
            @RequestHeader("Authorization") String jwt,
            @RequestPart("mainImage") MultipartFile mainImage,
            @RequestPart("subImages") MultipartFile[] subImages,
            @RequestPart("postRequest") @Valid PostRequest postRequest
    ) throws IOException {
        User user = userService.findUserByToken(jwt);
        return new ResponseEntity<>(postService.save(mainImage, subImages, postRequest, user.getUserId()), HttpStatus.CREATED);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDto> updatePostById(
            @RequestHeader("Authorization") String jwt,
            @RequestPart(value = "mainImage", required = false) MultipartFile mainImage,
            @RequestPart(value = "subImages", required = false) MultipartFile[] subImages,
            @PathVariable Long postId,
            @RequestBody @Valid PostRequest postRequest) {
        User user = userService.findUserByToken(jwt);
        return ResponseEntity.ok(postService.update(mainImage, subImages, postId, postRequest, user.getUserId()));
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePostById(@RequestHeader("Authorization") String jwt, @PathVariable Long postId) {
        User user = userService.findUserByToken(jwt);
        postService.deleteById(postId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    // Search Posts By Title
    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDto>> searchByTitle(@RequestParam String query) {
        return ResponseEntity.ok(postService.search(query));
    }
}
