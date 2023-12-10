package com.biratpoudel.blog.service.Impl;

import com.biratpoudel.blog.dto.PostDto;
import com.biratpoudel.blog.dto.PostRequest;
import com.biratpoudel.blog.dto.PostResponse;
import com.biratpoudel.blog.dto.mapper.PostDtoMapper;
import com.biratpoudel.blog.dto.mapper.RequestToPostMapper;
import com.biratpoudel.blog.exception.ResourceNotFoundException;
import com.biratpoudel.blog.exception.UnauthorizedException;
import com.biratpoudel.blog.model.Image;
import com.biratpoudel.blog.model.Post;
import com.biratpoudel.blog.model.User;
import com.biratpoudel.blog.repository.PostRepository;
import com.biratpoudel.blog.repository.UserRepository;
import com.biratpoudel.blog.service.FileService;
import com.biratpoudel.blog.service.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FileService fileService;
    private final UserRepository userRepository;
    private final PostDtoMapper postDtoMapper;
    private final RequestToPostMapper requestToPostMapper;


    @Value("${project.image}")
    private String path;


    public PostServiceImpl(PostRepository postRepository, FileService fileService, UserRepository userRepository, PostDtoMapper postDtoMapper, RequestToPostMapper requestToPostMapper) {
        this.postRepository = postRepository;
        this.fileService = fileService;
        this.userRepository = userRepository;
        this.postDtoMapper = postDtoMapper;
        this.requestToPostMapper = requestToPostMapper;
    }

    @Override
    public PostDto findById(Long id) {

        return postRepository.findById(id)
                             .map(postDtoMapper)
                             .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", id));
    }
    @Override
    public PostResponse findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepository.findAll(pageable);
        List<Post> posts = pagePost.getContent();

        List<PostDto> postDtos = posts.stream()
                    .map(postDtoMapper)
                    .toList();

        return new PostResponse(
                pagePost.getNumber(),
                pagePost.getSize(),
                pagePost.getTotalElements(),
                pagePost.getTotalPages(),
                pagePost.isLast(),
                postDtos
                );
    }

    @Override
    @Transactional
    public PostDto save(MultipartFile mainImage,
                        MultipartFile[] subImages,
                        PostRequest postRequest,
                        Long userId) throws IOException {

        Post post = requestToPostMapper.apply(postRequest);
        User user = userRepository.findById(userId)
                                  .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        String mainImageURL = fileService.uploadImage(path, mainImage);
        List<Image> subImagesURL = new ArrayList<>();
        Arrays.stream(subImages).forEach(
                (image) -> {
                    try {
                        subImagesURL.add(new Image(fileService.uploadImage(path, image), post));
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        post.setMainImage(mainImageURL);
        post.setSubImages(subImagesURL);
        post.setCreatedAt(new Date());
        post.setUpdatedAt(new Date());
        post.setUser(user);

        user.getPosts().add(post);

        return postDtoMapper.apply(postRepository.save(post));
    }

    @Override
    @Transactional
    public PostDto update(MultipartFile mainImage,
                          MultipartFile[] subImages,
                          Long postId,
                          PostRequest postRequest,
                          Long userId) {

        Post oldPost = postRepository.findById(postId)
                                     .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        if (!Objects.equals(oldPost.getUser().getUserId(), userId)) {
            throw new UnauthorizedException("You are not authorized to update this post.");
        }

        String mainImageURL = "";
        List<Image> subImagesURL = new ArrayList<>();

        if (mainImage != null) {
            try {
                mainImageURL = fileService.uploadImage(path, mainImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (subImages != null && subImages.length > 0) {
            Arrays.stream(subImages).forEach(
                    (image) -> {
                        try {
                            subImagesURL.add(new Image(fileService.uploadImage(path, image), oldPost));
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }

        oldPost.setTitle(postRequest.title());
        oldPost.setDescription(postRequest.description());
        oldPost.setUpdatedAt(new Date());
        oldPost.setMainImage(mainImageURL);
        oldPost.setSubImages(subImagesURL);
        postRepository.save(oldPost);
        return postDtoMapper.apply(oldPost);
    }

    @Override
    @Transactional
    public void deleteById(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                                  .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        if (!Objects.equals(post.getUser().getUserId(), userId)) {
            throw new UnauthorizedException("You are not authorized to delete this post.");
        }

        postRepository.delete(post);
    }

    @Override
    public List<PostDto> search(String keyword) {
        return postRepository.findByTitleContaining(keyword)
                             .stream()
                             .map(postDtoMapper)
                             .collect(Collectors.toList());
    }
}
