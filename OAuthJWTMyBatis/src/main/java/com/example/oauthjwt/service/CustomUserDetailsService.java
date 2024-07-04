package com.example.oauthjwt.service;

import com.example.oauthjwt.domain.User;
import com.example.oauthjwt.dto.CustomUserDetails;
import com.example.oauthjwt.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userData = userMapper.findByUsername(username);

        if (userData != null) {
            return new CustomUserDetails(userData);
        }
        return null;
    }
}
