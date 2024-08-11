package com.demo.cicd.controller;

import com.demo.cicd.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
@Slf4j
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/recordings/merge")
    public ResponseEntity<String> mergeVideos() {
        try {
            String success = videoService.mergeVideos();
            return ResponseEntity.ok(success);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error merging videos: " + e.getMessage());
        }
    }
}
