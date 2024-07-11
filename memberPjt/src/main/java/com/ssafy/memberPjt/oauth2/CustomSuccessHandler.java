package com.ssafy.memberPjt.oauth2;

import com.ssafy.memberPjt.dto.CustomOAuth2User;
import com.ssafy.memberPjt.dto.JwtTokenDTO;
import com.ssafy.memberPjt.entity.RefreshToken;
import com.ssafy.memberPjt.jwt.JWTUtil;
import com.ssafy.memberPjt.repository.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import static com.ssafy.memberPjt.util.CookieUtil.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("CustomSuccessHandler.onAuthenticationSuccess");

        //OAuth2User
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();

        System.out.println(customUserDetails);

        String username = customUserDetails.getUsername();

        System.out.println(username);

        String role = customUserDetails
                .getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getAuthority();

        JwtTokenDTO jwtToken = new JwtTokenDTO();
        jwtToken.setAccess(jwtUtil.createJwt("access", true, username, role, 1000 * 60 * 10L));
        jwtToken.setRefresh(jwtUtil.createJwt("refresh", true, username, role, 1000 * 60 * 60 * 24L));

        String access = jwtToken.getAccess();
        String refresh = jwtToken.getRefresh();

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        addRefreshToken(username, refresh);
        response.sendRedirect("http://localhost:5173/");
    }
    private void addRefreshToken(String username, String refresh) {
        LocalDateTime expirationDate = LocalDateTime.now().plusMonths(1);
        long expirationTime = Timestamp.valueOf(expirationDate).getTime();

        RefreshToken refreshToken = RefreshToken.builder()
            .username(username)
            .refresh(refresh)
            .expiration(expirationTime)
            .build();

        refreshRepository.save(refreshToken);
    }
}
