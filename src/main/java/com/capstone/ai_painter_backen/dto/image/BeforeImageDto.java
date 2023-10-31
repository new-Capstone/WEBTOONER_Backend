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
    public static class BeforeImageResponseDto {
        Long beforeImageId;
        Long userId;
        String beforeImageUri;
        List<AfterImageDto.AfterImageResponseDto> afterImageResponseDtos = new ArrayList<>();
    }

    /*
    TODO : refactoring (AfterImageService, BeforeImageService 분리)
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class BeforeImageCreateResponseDto {
        Long beforeImageId;
        Long userId;
        String beforeImageUri;
        List<MultipartFile> createdImages = new ArrayList<>();
    }
    */

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @Schema(description = "image file POST DTO")
    public static class BeforeImagePostDto {
        @Schema(description = "userId")
        Long userId;
        @Schema(description = "사진파일")
        MultipartFile beforeImageMultipartFile;
        @Schema(description = "표정")
        String expression;
        @Schema(description = "생성모델")
        String model;
        @Schema(description = "성별")
        String gender;
        @Schema(description = "웹툰 이름")
        String modelName;
    }

}
