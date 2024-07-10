package com.ssafy.memberPjt.controller;

import com.ssafy.memberPjt.dto.ApiResponse;
import com.ssafy.memberPjt.dto.CustomOAuth2User;
import com.ssafy.memberPjt.dto.CustomUserDetails;
import com.ssafy.memberPjt.dto.UserDTO;
import com.ssafy.memberPjt.entity.User;
import com.ssafy.memberPjt.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final UserService userService;

    @GetMapping("/")
    public String mainAPI(){
        return "main API";
    }

    @GetMapping("/api/my")
    public ResponseEntity<ApiResponse<User>> myAPI(@AuthenticationPrincipal Object principal){
        User user;
        if (principal instanceof CustomOAuth2User) {
            CustomOAuth2User oauth2User = (CustomOAuth2User) principal;
            user = userService.getUserByUsername(oauth2User.getUsername());
        } else if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            user = userService.getUserByUsername(userDetails.getUsername());
        } else {
            throw new IllegalArgumentException("Unsupported principal type: " + principal.getClass());
        }
        ApiResponse<User> responseData = new ApiResponse<>(
                "success",
                user, // data is null because it's not needed for this response
                "user Info",
                null
        );
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
