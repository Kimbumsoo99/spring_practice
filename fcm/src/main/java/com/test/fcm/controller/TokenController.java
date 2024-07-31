package com.test.fcm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tokens")
@CrossOrigin(origins = "http://localhost:5173")
public class TokenController {

    // 실제 구현에서는 데이터베이스를 사용해야 합니다. 여기서는 메모리 내 리스트를 사용합니다.
    private final List<String> tokens = new ArrayList<>();

    /**
     * 사용자 토큰을 저장하는 엔드포인트
     * 
     * @param payload - 클라이언트에서 전송된 토큰 데이터
     * @return 저장 성공 또는 실패 메시지
     */
    @PostMapping
    public ResponseEntity<String> storeToken(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        if (token != null && !token.isEmpty()) {
            tokens.add(token);
            return ResponseEntity.ok("Token stored successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid token");
        }
    }

    /**
     * 저장된 모든 토큰을 반환하는 엔드포인트
     * 
     * @return 저장된 모든 토큰 리스트
     */
    @GetMapping
    public ResponseEntity<List<String>> getTokens() {
        return ResponseEntity.ok(tokens);
    }
}