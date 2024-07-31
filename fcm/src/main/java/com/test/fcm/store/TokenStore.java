package com.test.fcm.store;

    import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenStore {

    private final Map<String, String> tokens = new HashMap<>();

    public void save(String userId, String token) {
        tokens.put(userId, token);
    }

    public String get(String userId) {
        return tokens.get(userId);
    }
}