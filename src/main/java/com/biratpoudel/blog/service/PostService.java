package com.biratpoudel.blog.service;

import com.biratpoudel.blog.dto.PostDto;
import com.biratpoudel.blog.dto.PostRequest;
import com.biratpoudel.blog.dto.PostResponse;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface PostService {

    PostDto findById(Long id);
    PostResponse findAll(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    PostDto save(MultipartFile mainImage, MultipartFile[] subImages, PostRequest postRequest, Long userId) throws IOException;
    PostDto update(MultipartFile mainImage, MultipartFile[] subImages, Long postId, PostRequest postRequest, Long userId);
    void deleteById(Long postId, Long userId);
    List<PostDto> search(String keyword);
}
