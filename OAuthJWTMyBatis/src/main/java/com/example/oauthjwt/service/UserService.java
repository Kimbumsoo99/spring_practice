package com.example.oauthjwt.service;

import com.example.oauthjwt.dto.JoinDTO;

public interface UserService {
    void joinProcess(JoinDTO joinDTO);

    boolean existsEmail(String email);

    boolean existsUsername(String username);
}
