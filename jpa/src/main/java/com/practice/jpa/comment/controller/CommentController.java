package com.practice.jpa.comment.controller;

import com.practice.jpa.comment.domain.Comment;
import com.practice.jpa.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}/{userId}")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @PathVariable Long postId, @PathVariable Long userId) {
        return ResponseEntity.ok(commentService.save(comment, postId, userId));
    }

    @GetMapping
    public ResponseEntity<List<Comment>> getAllComments() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }
}