package com.ssafy.memberPjt.service;

import com.ssafy.memberPjt.dto.JoinDTO;
import com.ssafy.memberPjt.dto.JwtTokenDTO;
import com.ssafy.memberPjt.dto.LoginDTO;
import com.ssafy.memberPjt.entity.User;
import com.ssafy.memberPjt.exception.EmailAlreadyExistsException;
import com.ssafy.memberPjt.exception.UserNotFoundException;
import com.ssafy.memberPjt.jwt.JWTUtil;
import com.ssafy.memberPjt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;


    public JwtTokenDTO loginProcess(LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        User user = userRepository.findByUsername(username);
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("아이디 또는 비밀번호가 잘못입력됐습니다.");
        }

        String role = user.getRole();

        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO();
        jwtTokenDTO.setAccess(jwtUtil.createJwt("access", username, role, 1000 * 60 * 10L));
        jwtTokenDTO.setRefresh(jwtUtil.createJwt("refresh", username, role, 1000 * 60 * 60 * 24L));
        return jwtTokenDTO;
    }

    @Transactional
    public void joinProcess(JoinDTO joinDTO) {
        log.info("UserServiceImpl.joinProcess - {}", joinDTO);
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        String name = joinDTO.getName();

        if (userRepository.existsByUsername(username)) {
            throw new EmailAlreadyExistsException("The email address is already in use.");
        }

        User data = new User();
        data.setUsername(username);
        data.setPassword(password);
        data.setRole("ROLE_ADMIN");
        data.setName(name);
        data.setEmail(email);

        System.out.println("data = " + data);

        try{
            userRepository.save(data);
        }catch (Exception e){
            e.printStackTrace();
        }

        // 로깅 추가
        if (userRepository.existsByUsername(joinDTO.getUsername())) {
            System.out.println("User successfully saved!");
        } else {
            System.out.println("User save failed.");
        }
    }

}
