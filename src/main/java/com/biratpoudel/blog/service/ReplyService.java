package com.biratpoudel.blog.service;

import com.biratpoudel.blog.dto.Votes;
import com.biratpoudel.blog.model.Reply;

public interface ReplyService {
    Reply save(Long commentId, Reply reply, Long userId);
    Reply update(Long commentId, Long replyId, Reply reply, Long userId);
    Reply updateVotes(Long commentId, Long replyId, Votes votes, Long userId);
    void delete(Long commentId, Long replyId, Long userId);
}
