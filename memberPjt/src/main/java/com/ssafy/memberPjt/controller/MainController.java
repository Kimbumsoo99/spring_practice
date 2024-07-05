package com.ssafy.memberPjt.controller;

import com.ssafy.memberPjt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String mainAPI(){
        return "main API";
    }

    @GetMapping("/api/my")
    public String myAPI(){
        System.out.println("MainController.myAPI");
        return "my APIs";
    }
}
