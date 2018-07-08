package com.baimurzin.gs.controller;

import com.baimurzin.gs.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/storage/")
public class BucketController {

    private final S3Service amazonClient;

    @PostMapping("/uploadFile")
    public ResponseEntity uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return new ResponseEntity<String>(this.amazonClient.uploadFile(file), HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteFile")
    public ResponseEntity deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl) ? new ResponseEntity("Deleted", HttpStatus.OK) :
                new ResponseEntity("Error", HttpStatus.BAD_REQUEST);
    }
}
