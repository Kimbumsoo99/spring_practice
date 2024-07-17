package com.practice.jpa.user.repository;

import com.practice.jpa.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}