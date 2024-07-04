package com.example.oauthjwt.service;

import com.example.oauthjwt.domain.User;
import com.example.oauthjwt.dto.JoinDTO;
import com.example.oauthjwt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Override
    public void joinProcess(JoinDTO joinDTO) {
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        int exist = userMapper.existsByUsername(username);
        boolean isExist = exist == 1;
        if (isExist) {
            return;
        }

        User data = new User();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");

        userMapper.save(data);
    }
}
