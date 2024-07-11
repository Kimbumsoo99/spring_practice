package com.ssafy.memberPjt.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.memberPjt.dto.JwtTokenDTO;
import com.ssafy.memberPjt.dto.LoginDTO;
import com.ssafy.memberPjt.entity.RefreshToken;
import com.ssafy.memberPjt.repository.RefreshRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import java.util.Date;
import java.util.Iterator;

import static com.ssafy.memberPjt.util.CookieUtil.createCookie;

@Slf4j
public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RefreshRepository refreshRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshRepository = refreshRepository;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthenticationTokenFilter");

        super.doFilter(request, response, chain);
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

        System.out.println(loginDTO);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        log.info("authToken - {}", authToken.toString());
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

        System.out.println(username + " : " + role);

        // token generate
//        String access = jwtUtil.createJwt("access", username, role, 600000L);
//        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);

//        addRefreshEntity(username, refresh, 86400000L);

        // response config
//        response.setHeader("access", access);

        JwtTokenDTO jwtToken = new JwtTokenDTO();
        jwtToken.setAccess(jwtUtil.createJwt("access", false, username, role, 1000 * 60 * 10L));
        jwtToken.setRefresh(jwtUtil.createJwt("refresh", false, username, role, 1000 * 60 * 60 * 24L));

        String access = jwtToken.getAccess();
        String refresh = jwtToken.getRefresh();

        response.setHeader("access", access);
        response.addCookie(createCookie("refresh", refresh));
        addRefreshToken(username, refresh);
        response.setStatus(HttpStatus.OK.value());
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

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("JwtAuthenticationTokenFilter.unsuccessfulAuthentication");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
