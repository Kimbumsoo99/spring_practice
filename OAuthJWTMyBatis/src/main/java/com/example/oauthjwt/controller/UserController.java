package com.example.oauthjwt.controller;

import com.example.oauthjwt.domain.User;
import com.example.oauthjwt.dto.ApiError;
import com.example.oauthjwt.dto.ApiResponse;
import com.example.oauthjwt.dto.JoinDTO;
import com.example.oauthjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> joinProcess(JoinDTO joinDTO) {
        log.info("UserController.joinProcess joinDTO - {}", joinDTO);
        userService.joinProcess(joinDTO);
        ApiResponse<Void> response = new ApiResponse<>(
                "success",
                null, // data is null because it's not needed for this response
                "Request successful",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
