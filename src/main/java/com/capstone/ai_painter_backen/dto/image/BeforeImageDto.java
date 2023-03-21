package com.capstone.ai_painter_backen.dto.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class BeforeImageDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class ResponseDto{
        Long beforeImageId;
        Long userId;
        String beforeImageUri;
        List<AfterImageDto.ResponseDto> afterImageResponseDtos = new ArrayList<>();
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @Schema(description = "image file POST DTO")
    public static class PostDto{
        Long userId;
        MultipartFile beforeImageMultipartFile;
    }
}
