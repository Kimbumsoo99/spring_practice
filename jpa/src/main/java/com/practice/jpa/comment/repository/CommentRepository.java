package com.practice.jpa.comment.repository;

import com.practice.jpa.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}