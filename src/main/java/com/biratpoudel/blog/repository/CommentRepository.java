package com.biratpoudel.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.biratpoudel.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
