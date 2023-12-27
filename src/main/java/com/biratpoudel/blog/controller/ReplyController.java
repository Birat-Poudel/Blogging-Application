package com.biratpoudel.blog.controller;

import com.biratpoudel.blog.dto.Votes;
import com.biratpoudel.blog.model.Reply;
import com.biratpoudel.blog.model.User;
import com.biratpoudel.blog.service.ReplyService;
import com.biratpoudel.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ReplyController {

    private final ReplyService replyService;
    private final UserService userService;

    public ReplyController(ReplyService replyService, UserService userService){
        this.replyService = replyService;
        this.userService = userService;
    }

    @PostMapping("/comments/{commentId}/replies")
    public ResponseEntity<Reply> save(@RequestHeader("Authorization") String jwt,
                                      @PathVariable Long commentId,
                                      @RequestBody Reply reply) {
        User user = userService.findUserByToken(jwt);
        return new ResponseEntity<>(replyService.save(commentId, reply, user.getUserId()), HttpStatus.CREATED);
    }

    @PutMapping("/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Reply> update(@RequestHeader("Authorization") String jwt,
                                        @PathVariable Long commentId,
                                        @PathVariable Long replyId,
                                        @RequestBody Reply reply) {
        User user = userService.findUserByToken(jwt);
        return ResponseEntity.ok(replyService.update(commentId, replyId, reply, user.getUserId()));
    }

    @PutMapping("/comments/{commentId}/replies/{replyId}/votes")
    public ResponseEntity<Reply> updateVotes(@RequestHeader("Authorization") String jwt,
                                             @PathVariable Long commentId,
                                             @PathVariable Long replyId,
                                             @RequestBody Votes votes) {
        User user = userService.findUserByToken(jwt);
        return ResponseEntity.ok(replyService.updateVotes(commentId, replyId, votes, user.getUserId()));
    }


    @DeleteMapping("/comments/{commentId}/replies/{replyId}")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String jwt,
                                       @PathVariable Long commentId,
                                       @PathVariable Long replyId) {
        User user = userService.findUserByToken(jwt);
        replyService.delete(commentId, replyId, user.getUserId());
        return ResponseEntity.noContent().build();
    }
}
