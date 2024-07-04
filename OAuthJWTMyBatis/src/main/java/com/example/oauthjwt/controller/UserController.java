package com.example.oauthjwt.controller;

import com.example.oauthjwt.dto.JoinDTO;
import com.example.oauthjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {
        userService.joinProcess(joinDTO);
        return "ok";
    }
}
