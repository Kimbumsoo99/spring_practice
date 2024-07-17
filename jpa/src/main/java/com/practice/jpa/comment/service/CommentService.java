package com.practice.jpa.comment.service;

import com.practice.jpa.comment.domain.Comment;
import com.practice.jpa.comment.repository.CommentRepository;
import com.practice.jpa.exception.ResourceNotFoundException;
import com.practice.jpa.post.domain.Post;
import com.practice.jpa.post.repository.PostRepository;
import com.practice.jpa.user.domain.User;
import com.practice.jpa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment save(Comment comment, Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        comment.setPost(post);
        comment.setUser(user);
        return commentRepository.save(comment);
    }

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
    }
}