package com.capstone.ai_painter_backen.dto.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class TutorDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostDto {
        @Schema
        Long userId;
        @Schema
        String description;
        @Schema
        List<String> categoryNames;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema

    public static class ResponseDto {
        Long tutorId;
        String description;
        List<String> categoryNames = new ArrayList<>();
        List<TuteeEntity> tuteeEntities = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema

    public static class DeleteDto {
        Long tutorId; // id 를 이용해서 삭제

        @Override
        public String toString() {
            return "DeleteDto{" +
                    "tutorId=" + tutorId +
                    '}';
        }
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "유저 아이디로 user 에 저장 string type으로 category 받음")
    public static class PatchDto {
        Long tutorId;
        Long userId;
        String description;
        List<String> categoryNames;
    }

}
