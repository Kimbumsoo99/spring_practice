package com.ssafy.memberPjt.oauth2;


import com.ssafy.memberPjt.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ssafy.memberPjt.util.CookieUtil.createCookie;

@Slf4j
@Component
public class RedirectUrlCookieFilter extends OncePerRequestFilter {
    public static final String REDIRECT_URI_PARAM = "redirect_uri";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("RedirectUrlCookieFilter.doFilterInternal {}", REDIRECT_URI_PARAM);
        if (request.getRequestURI().startsWith("/auth/authorize")) {
            try {
                String redirectUri = request.getParameter(REDIRECT_URI_PARAM);

                Cookie cookie = createCookie(REDIRECT_URI_PARAM, redirectUri, 180);
            } catch (Exception e) {
                logger.error("user authentication in security context", e);
                log.info("Unauthorized request");
            }
        }
        filterChain.doFilter(request, response);
    }
}
