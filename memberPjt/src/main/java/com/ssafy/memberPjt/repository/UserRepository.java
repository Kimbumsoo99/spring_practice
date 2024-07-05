package com.ssafy.memberPjt.repository;

import com.ssafy.memberPjt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    User findByUsername(String username);
}
