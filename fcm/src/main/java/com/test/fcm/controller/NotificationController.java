package com.test.fcm.controller;

import com.test.fcm.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/fcm")
public class NotificationController {

    private Map<String, String> tokenStore = new ConcurrentHashMap<>();

    @Autowired
    private PushNotificationService pushNotificationService;

    @PostMapping("/register")
    public void registerToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        tokenStore.put("defaultUser", token);
    }

    @PostMapping("/send")
    public void send(@RequestParam String title, @RequestParam String body) {
        String token = tokenStore.get("defaultUser");
        if (token != null) {
            pushNotificationService.sendMessage(token, title, body);
        }
    }

    @GetMapping("/sendTest")
    public void sendTest() {
        String token = tokenStore.get("defaultUser");
        if (token != null) {
            pushNotificationService.sendTestMessage(token);
        }
    }
}