package com.capstone.ai_painter_backen.dto;

import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class UserDto {
    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserPostDto {
        private String username;
        private String loginId;
        private String password;
        private String description;
        private String profileImage;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDto {
        private Long userid;
        private String username;
        private String loginId;
        private String description;
        private String profileImage;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DeleteDto {
        private Long userid;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PatchDto {
        private Long userid;
        private String password;
        private String description;
        private String profileImage;
        private TutorEntity tutorEntity;
        private TuteeEntity tuteeEntity;
    }
}
