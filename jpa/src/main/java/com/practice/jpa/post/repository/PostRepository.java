package com.practice.jpa.post.repository;

import com.practice.jpa.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {}