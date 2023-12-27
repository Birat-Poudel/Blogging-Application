package com.biratpoudel.blog.service.Impl;

import com.biratpoudel.blog.dto.Votes;
import com.biratpoudel.blog.exception.ResourceNotFoundException;
import com.biratpoudel.blog.model.Comment;
import com.biratpoudel.blog.model.Reply;
import com.biratpoudel.blog.model.User;
import com.biratpoudel.blog.repository.CommentRepository;
import com.biratpoudel.blog.repository.ReplyRepository;
import com.biratpoudel.blog.repository.UserRepository;
import com.biratpoudel.blog.service.ReplyService;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    public ReplyServiceImpl(CommentRepository commentRepository, ReplyRepository replyRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Reply save(Long commentId, Reply reply, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        comment.getReplies().add(reply);
        user.getReplies().add(reply);
        commentRepository.save(comment);
        return reply;
    }

    @Override
    public Reply update(Long commentId, Long replyId, Reply reply, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        Reply replyToUpdate = comment.getReplies()
                .stream()
                .filter(rep -> rep.getReplyId().equals(replyId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Reply", "Reply Id", replyId));

        replyToUpdate.setReplyText(reply.getReplyText());
        commentRepository.save(comment);
        return replyToUpdate;
    }

    @Override
    public Reply updateVotes(Long commentId, Long replyId, Votes votes, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        Reply replyToUpdate = comment.getReplies()
                .stream()
                .filter(rep -> rep.getReplyId().equals(replyId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Reply", "Reply Id", replyId));

        Integer likes = votes.likes();

        if (likes != null) {
            replyToUpdate.setLikes(replyToUpdate.getLikes() + 1);
        } else {
            replyToUpdate.setDislikes(replyToUpdate.getDislikes() + 1);
        }

        commentRepository.save(comment);
        return replyToUpdate;
    }

    @Override
    public void delete(Long commentId, Long replyId, Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Comment Id", commentId));

        Reply replyToUpdate = comment.getReplies()
                .stream()
                .filter(rep -> rep.getReplyId().equals(replyId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Reply", "Reply Id", replyId));

        replyRepository.deleteById(replyToUpdate.getReplyId());
        commentRepository.save(comment);
    }
}
