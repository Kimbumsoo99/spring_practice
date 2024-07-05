package com.ssafy.memberPjt.controller;

import com.ssafy.memberPjt.dto.ApiResponse;
import com.ssafy.memberPjt.dto.JoinDTO;
import com.ssafy.memberPjt.dto.JwtTokenDTO;
import com.ssafy.memberPjt.dto.LoginDTO;
import com.ssafy.memberPjt.service.UserService;
import com.ssafy.memberPjt.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ssafy.memberPjt.util.CookieUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> joinProcess(@RequestBody JoinDTO joinDTO) {
        log.info("UserController.joinProcess joinDTO - {}", joinDTO);
        userService.joinProcess(joinDTO);
        ApiResponse<Void> response = new ApiResponse<>(
                "success",
                null, // data is null because it's not needed for this response
                "joinProcess successful",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> loginProcess(@RequestBody LoginDTO loginDTO, HttpServletResponse response) {
        log.info("UserController.loginProcess loginDTO - {}", loginDTO);

        JwtTokenDTO jwtToken = userService.loginProcess(loginDTO); // Cookie 생성 후 Authorization에 삽입해서 반환

        String access = jwtToken.getAccess();
        String refresh = jwtToken.getRefresh();

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));

        ApiResponse<Void> responseData = new ApiResponse<>(
                "success",
                null, // data is null because it's not needed for this response
                "loginProcess successful",
                null
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
