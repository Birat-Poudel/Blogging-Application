package com.biratpoudel.blog.controller;

import com.biratpoudel.blog.dto.Votes;
import com.biratpoudel.blog.model.Comment;
import com.biratpoudel.blog.model.User;
import com.biratpoudel.blog.service.CommentService;
import com.biratpoudel.blog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;


    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> save(@RequestHeader("Authorization") String jwt,
                                        @PathVariable Long postId,
                                        @RequestBody Comment comment) {
        User user = userService.findUserByToken(jwt);
        return new ResponseEntity<>(commentService.save(postId, comment, user.getUserId()), HttpStatus.CREATED);
    }

     @PutMapping("/posts/{postId}/comments/{commentId}")
     public ResponseEntity<Comment> update(@RequestHeader("Authorization") String jwt,
                                           @PathVariable Long postId,
                                           @PathVariable Long commentId,
                                           @RequestBody Comment comment) {
         User user = userService.findUserByToken(jwt);
         return ResponseEntity.ok(commentService.update(postId, commentId, comment, user.getUserId()));
     }

     @PutMapping("/posts/{postId}/comments/{commentId}/votes")
     public ResponseEntity<Comment> updateVotes(@RequestHeader("Authorization") String jwt,
                                                @PathVariable Long postId,
                                                @PathVariable Long commentId,
                                                @RequestBody Votes votes) {
         User user = userService.findUserByToken(jwt);
         return ResponseEntity.ok(commentService.updateVotes(postId, commentId, votes, user.getUserId()));
     }


    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String jwt,
                                       @PathVariable Long postId,
                                       @PathVariable Long commentId) {
        User user = userService.findUserByToken(jwt);
        commentService.delete(postId, commentId, user.getUserId());
        return ResponseEntity.noContent().build();
    }

}
