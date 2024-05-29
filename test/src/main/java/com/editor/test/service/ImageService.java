package com.editor.test.service;

import com.editor.test.config.S3Config;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;

public interface ImageService {
    public String imageUpload(MultipartRequest request) throws IOException;
}
