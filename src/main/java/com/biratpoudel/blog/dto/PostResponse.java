package com.biratpoudel.blog.dto;

import java.util.List;

public record PostResponse(
        Integer pageNumber,
        Integer pageSize,
        Long totalElements,
        Integer totalPages,
        Boolean isLastPage,
        List<PostDto> content
) {
}
