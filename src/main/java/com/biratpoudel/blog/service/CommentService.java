package com.biratpoudel.blog.service;

import com.biratpoudel.blog.dto.Votes;
import com.biratpoudel.blog.model.Comment;

import java.util.List;

public interface CommentService {
    Comment save(Long postId, Comment comment);
    Comment update(Long postId, Long commentId, Comment comment);
    Comment updateVotes(Long postId, Long commentId, Votes votes);
    void delete(Long postId, Long commentId);
}
