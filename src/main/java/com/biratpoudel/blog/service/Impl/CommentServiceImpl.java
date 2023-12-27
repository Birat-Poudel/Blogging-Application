package com.biratpoudel.blog.service.Impl;

import com.biratpoudel.blog.dto.Votes;
import com.biratpoudel.blog.exception.ResourceNotFoundException;
import com.biratpoudel.blog.model.Comment;
import com.biratpoudel.blog.model.Post;
import com.biratpoudel.blog.model.User;
import com.biratpoudel.blog.repository.CommentRepository;
import com.biratpoudel.blog.repository.PostRepository;
import com.biratpoudel.blog.repository.UserRepository;
import com.biratpoudel.blog.service.CommentService;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment save(Long postId, Comment comment, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
        post.getComments().add(comment);

        user.getComments().add(comment);
        postRepository.save(post);
        return comment;
    }

    @Override
    public Comment update(Long postId, Long commentId, Comment comment, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        Comment commentToUpdate = post
                .getComments()
                .stream()
                .filter(comm -> comm.getCommentId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        commentToUpdate.setComment(comment.getComment());
        postRepository.save(post);
        return commentToUpdate;
    }

    @Override
    public Comment updateVotes(Long postId, Long commentId, Votes votes, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        Comment commentToUpdate = post
                .getComments()
                .stream()
                .filter(comm -> comm.getCommentId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        Integer likes = votes.likes();

        if (likes != null) {
            commentToUpdate.setLikes(commentToUpdate.getLikes() + 1);
        } else {
            commentToUpdate.setDislikes(commentToUpdate.getDislikes() + 1);
        }

        postRepository.save(post);
        return commentToUpdate;
    }

    @Override
    public void delete(Long postId, Long commentId, Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Post post = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));

        Comment commentToDelete = post
                .getComments()
                .stream()
                .filter(comment -> comment.getCommentId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        commentRepository.deleteById(commentToDelete.getCommentId());
        postRepository.save(post);
    }

}
