package com.capstone.ai_painter_backen.dto;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
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
    @Schema(description = "유저 아이디로 user 에 저장 string type으로 category 받음")
    public static class TutorRequestPostDto{
        Long userId;
        String description;
        List<String> category;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "토큰과 함께 유저 정보를 반환하는 dto")
    public static class TutorResponseDto{
        UserEntity user;//userDto 로 대체될 예정
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Schema(description = "토큰과 함께 유저 정보를 반환하는 dto")
    public static class TutorRequestDeleteDto{
        Long tutorId; // id 를 이용해서 삭제
    }

}
