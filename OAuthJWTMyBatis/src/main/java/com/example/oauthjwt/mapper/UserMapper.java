package com.example.oauthjwt.mapper;

import com.example.oauthjwt.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    void save(User user);
    int existsByUsername(String username);

    int existsByEmail(String email);
}
