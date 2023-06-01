package com.capstone.ai_painter_backen.dto.mentor;

import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
public class TutorDto {
    @Schema
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TutorPostDto {
        @Schema
        Long userId;
        @Schema
        String description;
        @Schema
        List<String> categoryNames;
    }
    @Schema
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TutorResponseDto {
        @Schema
        Long tutorId;
        @Schema
        String description;
        @Schema
        List<String> categoryNames = new ArrayList<>();
        @Schema
        List<TuteeDto.TuteeResponseDto> tuteeResponseDtos = new ArrayList<>();
    }

    @Schema
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TutorDeleteDto {
        @Schema
        Long tutorId; // id 를 이용해서 삭제

        @Override
        public String toString() {
            return "DeleteDto{" +
                    "tutorId=" + tutorId +
                    '}';
        }
    }


    @Schema(description = "유저 아이디로 user 에 저장 string type으로 category 받음")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TutorPatchDto {
        Long tutorId;
        Long userId;
        String description;
        List<String> categoryNames;
    }

    @Schema(description = "유저 아이디로 user 에 저장 string type으로 category 받음")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class TutorResponseDtoIdAndImage {
        private Long tutorId;
        private String url;
    }


}
