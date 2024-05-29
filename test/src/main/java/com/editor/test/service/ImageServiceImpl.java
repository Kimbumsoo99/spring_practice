package com.editor.test.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.editor.test.config.S3Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService{

    private S3Config s3Config;
    @Value("${file.path}")
    private String localLocation;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public ImageServiceImpl(S3Config s3Config) {
        this.s3Config = s3Config;
    }

    @Override
    public String imageUpload(MultipartRequest request) throws IOException {
        // request 인자에서 이미지 파일을 뽑아냄
        MultipartFile file = request.getFile("upload");

        // 뽑아낸 이미지에서 이름, 확장자 추출
        String fileName = file.getOriginalFilename();
        String ext = fileName.substring(fileName.indexOf("."));

        // 유일 이름 지정을 위한 UUID 이름 생성
        String uuidFileName = UUID.randomUUID() + ext;

        // 서버환경에 저장할 경로 생성
        String localPath = localLocation + uuidFileName;

        // 서버 환경에 이미지 파일 저장
        File localFile = new File(localPath);
        file.transferTo(localFile);


//        s3Config.amazonS3Client().putObject(new PutObjectRequest("버킷", "파일명", "서버의 저저장한 파일"));

        // ACL 설정은 보안과 관련된 설정으로, ACL 설정과 함께 내부 인자값으로 PublicRead를 넣어주면, 외부에서 읽을 수 있게 해주는 것
        s3Config.amazonS3Client().putObject(new PutObjectRequest(bucket, uuidFileName, localFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        // S3에 올린 이미지의 src 주소를 획득할 수 있다.
        String s3Url = s3Config.amazonS3Client().getUrl(bucket, uuidFileName).toString();


        // 서버에 저장한 이미지를 삭제
        localFile.delete();

        return s3Url;
    }

}
