package com.example.oauthjwt.service;

import com.example.oauthjwt.domain.User;
import com.example.oauthjwt.dto.JoinDTO;
import com.example.oauthjwt.exception.EmailAlreadyExistsException;
import com.example.oauthjwt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    @Override
    public void joinProcess(JoinDTO joinDTO) {
        log.info("UserServiceImpl.joinProcess - {}", joinDTO);
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        String name = joinDTO.getName();

        if (existsEmail(email) || existsUsername(username)) {
            throw new EmailAlreadyExistsException("The email address is already in use.");
        }

        User data = new User();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setRole("ROLE_ADMIN");
        data.setName(name);
        data.setEmail(email);

        userMapper.save(data);
    }

    @Override
    public boolean existsEmail(String email) {
        int existEmail = userMapper.existsByEmail(email);
        boolean isExist = existEmail == 1;
        if (!isExist) {
            return true;
        }
        return false;
    }

    @Override
    public boolean existsUsername(String username) {
        int existUsername = userMapper.existsByUsername(username);
        boolean isExist = existUsername == 1;
        if (!isExist) {
            return true;
        }
        return false;
    }
}
