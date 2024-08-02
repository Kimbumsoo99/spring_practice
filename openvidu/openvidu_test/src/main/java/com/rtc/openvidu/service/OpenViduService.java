package com.rtc.openvidu.service;

import io.openvidu.java.client.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.auth.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.*;
import org.apache.hc.client5.http.ssl.*;
import org.apache.hc.core5.ssl.*;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OpenViduService {

    @Value("${openvidu.url}")
    private String OPENVIDU_URL;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    private OpenVidu openVidu;

    @PostConstruct
    public void init() {
        this.openVidu = new OpenVidu(OPENVIDU_URL, OPENVIDU_SECRET);
    }

    public String createSession(Map<String, Object> properties)
            throws OpenViduJavaClientException, OpenViduHttpException {
        SessionProperties sessionProperties = SessionProperties.fromJson(properties).build();
        Session session = openVidu.createSession(sessionProperties);
        return session.getSessionId();
    }

    public String createConnection(String sessionId, Map<String, Object> properties)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Session session = openVidu.getActiveSession(sessionId);
        if (session == null) {
            return null;
        }
        ConnectionProperties connectionProperties = ConnectionProperties.fromJson(properties).build();
        Connection connection = session.createConnection(connectionProperties);
        return connection.getToken();
    }

    public String startRecording(String sessionId, RecordingProperties properties)
            throws OpenViduJavaClientException, OpenViduHttpException {
        Recording recording = openVidu.startRecording(sessionId, properties);
        return recording.getId();
    }

    public Recording stopRecording(String recordingId)
            throws OpenViduJavaClientException, OpenViduHttpException {
        return openVidu.stopRecording(recordingId);
    }

    public List<Recording> listRecordings()
            throws OpenViduJavaClientException, OpenViduHttpException {
        return openVidu.listRecordings();
    }

    public Recording getRecording(String recordingId)
            throws OpenViduJavaClientException, OpenViduHttpException {
        return openVidu.getRecording(recordingId);
    }
}
