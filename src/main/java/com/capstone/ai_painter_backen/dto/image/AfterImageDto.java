package com.capstone.ai_painter_backen.dto.image;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class AfterImageDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema
    public static class ResponseDto{
        Long afterImageId;
        String afterImageUri;
        Long beforeImageId;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema

    public static class PostDto{
        MultipartFile multipartFile;
        Long beforeImageUri; //변환 전의 이미지의 id 값 필요함.
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder @Schema

    public static class DeleteDto{
        Long afterImageId;//아이디를 이용해서 삭제 사실 필요 없음.
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class GetDto{
        Long getAfterImageDto;//사실 필요없음.
    }
}
