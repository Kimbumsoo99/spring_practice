package com.ssafy.memberPjt.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.memberPjt.dto.LoginDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;

import static com.ssafy.memberPjt.util.CookieUtil.createCookie;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthenticationTokenFilter.doFilter");
        chain.doFilter(request, response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("JwtAuthenticationTokenFilter.attemptAuthentication");
        LoginDTO loginDTO;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginDTO = objectMapper.readValue(messageBody, LoginDTO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        log.info("username - {}", username);
        log.info("password - {}", password);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("JwtAuthenticationTokenFilter.successfulAuthentication");
        String username = authResult.getName();

        Collection<? extends GrantedAuthority> collection = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> it = collection.iterator();
        GrantedAuthority auth = it.next();
        String role = auth.getAuthority();

        // token generate
//        String access = jwtUtil.createJwt("access", username, role, 600000L);
//        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

//        addRefreshEntity(username, refresh, 86400000L);

        // response config
//        response.setHeader("access", access);

        String token = jwtUtil.createJwt("", username, role, 60*60*60*60L);
        response.addCookie(createCookie("Authorization", token));
        System.out.println("token = " + token);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("JwtAuthenticationTokenFilter.unsuccessfulAuthentication");

    }
}
