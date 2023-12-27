package com.biratpoudel.blog.service;

import com.biratpoudel.blog.dto.Votes;
import com.biratpoudel.blog.model.Comment;

import java.util.List;

public interface CommentService {
    Comment save(Long postId, Comment comment, Long userId);
    Comment update(Long postId, Long commentId, Comment comment, Long userId);
    Comment updateVotes(Long postId, Long commentId, Votes votes, Long userId);
    void delete(Long postId, Long commentId, Long userId);
}
