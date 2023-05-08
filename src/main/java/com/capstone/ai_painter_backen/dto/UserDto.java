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
        @Schema
        private String username;
        @Schema
        private String userEmail;
        @Schema
        private String password;
        @Schema
        private String description;
        @Schema
        private String profileImage;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserResponseDto {
        @Schema
        private Long userId;
        @Schema
        private String username;
        @Schema
        private String userEmail;
        @Schema
        private String description;
        @Schema
        private String profileImage;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserDeleteDto {
        @Schema
        private Long userId;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserPatchDto {
        @Schema
        private Long userId;
        @Schema
        private String password;
        @Schema
        private String description;
        @Schema
        private String profileImage;
        @Schema
        private TutorEntity tutorEntity;
        @Schema
        private TuteeEntity tuteeEntity;
    }
}
