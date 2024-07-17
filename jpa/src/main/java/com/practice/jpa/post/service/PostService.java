package com.practice.jpa.post.service;

import com.practice.jpa.exception.ResourceNotFoundException;
import com.practice.jpa.post.domain.Post;
import com.practice.jpa.post.repository.PostRepository;
import com.practice.jpa.user.domain.User;
import com.practice.jpa.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post save(Post post, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        post.setUser(user);
        return postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }
}