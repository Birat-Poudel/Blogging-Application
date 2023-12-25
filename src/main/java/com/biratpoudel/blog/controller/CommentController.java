package com.biratpoudel.blog.controller;

import com.biratpoudel.blog.dto.Votes;
import com.biratpoudel.blog.model.Comment;
import com.biratpoudel.blog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<Comment> save(@PathVariable Long postId,
                                         @RequestBody Comment comment) {
        return new ResponseEntity<>(commentService.save(postId, comment), HttpStatus.CREATED);
    }

     @PutMapping("/posts/{postId}/comments/{commentId}")
     public ResponseEntity<Comment> update(@PathVariable Long postId,
                                           @PathVariable Long commentId,
                                           @RequestBody Comment comment) {
         return ResponseEntity.ok(commentService.update(postId, commentId, comment));
     }

     @PutMapping("/posts/{postId}/comments/{commentId}")
     public ResponseEntity<Comment> updateVotes(@PathVariable Long postId,
                                                @PathVariable Long commentId,
                                                @RequestBody Votes votes) {
         return ResponseEntity.ok(commentService.updateVotes(postId, commentId, votes));
     }


    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Long postId,
                                       @PathVariable Long commentId) {
        commentService.delete(postId, commentId);
        return ResponseEntity.noContent().build();
    }

}
