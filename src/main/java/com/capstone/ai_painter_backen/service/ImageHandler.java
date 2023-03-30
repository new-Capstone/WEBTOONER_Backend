package com.capstone.ai_painter_backen.service;

import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageHandler {

    public S3ImageInfo postImage(final MultipartFile multipartFile);//s3에 이미지 저장후에 imageUri 넘김
    public S3ImageInfo deleteImage(final String imgUri);// s3에 이미지 삭제한후 imageUri 넘김.
    public S3ImageInfo postListImage(List<MultipartFile> multipartFileList);
    public S3ImageInfo deleteListImage(List<String> imageUriList);

}
