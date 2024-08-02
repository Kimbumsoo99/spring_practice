package com.rtc.openvidu.controller;

import com.rtc.openvidu.service.OpenViduService;
import io.openvidu.java.client.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/video")
@Slf4j
@RequiredArgsConstructor
public class VideoController {

    private final OpenViduService openViduService;
    private OpenVidu openVidu;
    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    @Value("${openvidu.url}")
    String openviduUrl;
    @Value("${openvidu.secret}")
    String openviduSecret;

    @PostConstruct
    public void init() {
        this.openVidu = new OpenVidu(openviduUrl, openviduSecret);
    }

    @GetMapping("/get-token")
    public ResponseEntity<String> getToken(@RequestParam String sessionName) {
        try {
            Session session;
            if (this.sessions.containsKey(sessionName)) {
                session = this.sessions.get(sessionName);
            } else {
                session = this.openVidu.createSession();
                this.sessions.put(sessionName, session);
            }
            String token = session.generateToken(new TokenOptions.Builder().build());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param params The Session properties
     * @return The Session ID
     */
    @PostMapping("/sessions")
    public ResponseEntity<String> initializeSession(@RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        String sessionId = openViduService.createSession(params);
        return new ResponseEntity<>(sessionId, HttpStatus.OK);
    }

    /**
     * @param sessionId The Session in which to create the Connection
     * @param params    The Connection properties
     * @return The Token associated to the Connection
     */
    @PostMapping("/sessions/{sessionId}/connections")
    public ResponseEntity<String> createConnection(@PathVariable("sessionId") String sessionId,
                                                   @RequestBody(required = false) Map<String, Object> params)
            throws OpenViduJavaClientException, OpenViduHttpException {
        log.info("Received request to create connection for session: {}", sessionId);
        log.info("Request parameters: {}", params);

        String token = openViduService.createConnection(sessionId, params);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    /**
     * Start recording a session
     * @param sessionId The Session ID
     * @return The Recording ID
     */
    @PostMapping("/sessions/{sessionId}/recordings/start")
    public ResponseEntity<String> startRecording(@PathVariable("sessionId") String sessionId)
            throws OpenViduJavaClientException, OpenViduHttpException {
        System.out.println("녹화시작");
        RecordingProperties properties = new RecordingProperties.Builder()
                .outputMode(Recording.OutputMode.COMPOSED)
                .build();
        String recordingId = openViduService.startRecording(sessionId, properties);
        return new ResponseEntity<>(recordingId, HttpStatus.OK);
    }

    /**
     * Stop recording a session
     * @param recordingId The Recording ID
     * @return The stopped Recording
     */
    @PostMapping("/recordings/stop/{recordingId}")
    public ResponseEntity<Recording> stopRecording(@PathVariable("recordingId") String recordingId)
            throws OpenViduJavaClientException, OpenViduHttpException {
        System.out.println("녹화중지");
        Recording recording = openViduService.stopRecording(recordingId);
        return new ResponseEntity<>(recording, HttpStatus.OK);
    }

    /**
     * Get a list of all recordings
     * @return The list of recordings
     */
    @GetMapping("/recordings")
    public ResponseEntity<List<Recording>> listRecordings() throws OpenViduJavaClientException, OpenViduHttpException {
        List<Recording> recordings = openViduService.listRecordings();
        return new ResponseEntity<>(recordings, HttpStatus.OK);
    }

    /**
     * Get a specific recording by ID
     * @param recordingId The Recording ID
     * @return The Recording
     */
    @GetMapping("/recordings/{recordingId}")
    public ResponseEntity<Recording> getRecording(@PathVariable("recordingId") String recordingId)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Recording recording = openViduService.getRecording(recordingId);
        return new ResponseEntity<>(recording, HttpStatus.OK);
    }
}