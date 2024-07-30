package com.test.fcm.controller;

import com.test.fcm.service.PushNotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final PushNotificationService pushNotificationService;

    public NotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PostMapping("/send")
    public void sendNotification(@RequestParam String token, @RequestParam String title, @RequestParam String body) {
        pushNotificationService.sendNotification(token, title, body);
    }
}