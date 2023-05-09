package com.capstone.ai_painter_backen.controller.Message.test;

import com.capstone.ai_painter_backen.service.awsS3.S3FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class S3TestController {

    S3FileService s3FileService;

    @DeleteMapping()
    public ResponseEntity<?> deleteFileS3(String fileUri){
        return ResponseEntity.ok().body(s3FileService.deleteMultiFile(fileUri));
    }


}
