package com.ssafy.memberPjt.controller;

import com.ssafy.memberPjt.dto.*;
import com.ssafy.memberPjt.entity.RefreshToken;
import com.ssafy.memberPjt.jwt.JWTUtil;
import com.ssafy.memberPjt.repository.RefreshRepository;
import com.ssafy.memberPjt.service.UserService;
import com.ssafy.memberPjt.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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

import java.util.Date;

import static com.ssafy.memberPjt.util.CookieUtil.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;

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

        addRefreshToken(loginDTO.getUsername(), refresh, 86400000L);

        ApiResponse<Void> responseData = new ApiResponse<>(
                "success",
                null, // data is null because it's not needed for this response
                "loginProcess successful",
                null
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse<Void>> logoutProcess() {
//        log.info("UserController.logoutProcess");
//
//        ApiResponse<Void> responseData = new ApiResponse<>(
//                "success",
//                null, // data is null because it's not needed for this response
//                "logout Success",
//                null
//        );
//        return new ResponseEntity<>(responseData, HttpStatus.OK);
//    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<Void>> reissue(HttpServletRequest request, HttpServletResponse response) {
        String refresh = null;

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("refresh")) {
                refresh = cookie.getValue();
            }
        }

        ApiResponse<Void> responseData = new ApiResponse<>(
                "error",
                null,
                null,
                null
        );

        if (refresh == null) {
            responseData.setMessage("refresh token null");
            responseData.setError(new ApiError("400", "refresh token null"));
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            responseData.setMessage("refresh token expired");
            responseData.setError(new ApiError("400", "refresh token expired"));
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        // 토큰이 refresh인지 확인 (발급시 페이로드에 명시)
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            responseData.setMessage("invalid refresh token");
            responseData.setError(new ApiError("400", "invalid refresh token"));
            return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
        }

        String username = jwtUtil.getUsername(refresh);
        String role = jwtUtil.getRole(refresh);

        //make new JWT
        String newAccess = jwtUtil.createJwt("access", username, role, 600000L);
        String newRefresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

        //response
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        //Refresh 토큰 저장 DB에 기존의 Refresh 토큰 삭제 후 새 Refresh 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefreshToken(username, newRefresh, 86400000L);

        responseData.setStatus("success");
        responseData.setMessage("access Token Generate");

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    private void addRefreshToken(String username, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUsername(username);
        refreshToken.setRefresh(refresh);
        refreshToken.setExpiration(date.toString());

        refreshRepository.save(refreshToken);
    }

}
