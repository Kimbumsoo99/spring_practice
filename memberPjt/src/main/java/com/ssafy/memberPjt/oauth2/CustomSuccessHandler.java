package com.ssafy.memberPjt.oauth2;

import com.ssafy.memberPjt.dto.CustomOAuth2User;
import com.ssafy.memberPjt.dto.JwtTokenDTO;
import com.ssafy.memberPjt.entity.RefreshToken;
import com.ssafy.memberPjt.jwt.JWTUtil;
import com.ssafy.memberPjt.repository.RefreshRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

        String username = customUserDetails.getUsername();

        String role = customUserDetails
                .getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(IllegalAccessError::new)
                .getAuthority();

        JwtTokenDTO jwtToken = new JwtTokenDTO();
        jwtToken.setAccess(jwtUtil.createJwt("access", username, role, 1000 * 60 * 10L));
        jwtToken.setRefresh(jwtUtil.createJwt("refresh", username, role, 1000 * 60 * 60 * 24L));

        String access = jwtToken.getAccess();
        String refresh = jwtToken.getRefresh();

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        addRefreshToken(username, refresh, 86400000L);
        response.sendRedirect("http://localhost:5173/");
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
