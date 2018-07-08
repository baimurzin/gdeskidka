package com.baimurzin.gs.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    String uploadFile(MultipartFile multipartFile);
    void uploadFile(String keyName, String uploadFilePath);
    boolean deleteFileFromS3Bucket(String url);
}
