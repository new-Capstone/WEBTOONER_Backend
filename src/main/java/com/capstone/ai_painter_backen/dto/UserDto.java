package com.capstone.ai_painter_backen.dto;

import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
        private String loginId;
        @Schema
        private String password;
        @Schema
        private String description;
        @Schema
        private MultipartFile profileImage;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserResponseDto {
        @Schema
        private Long userid;
        @Schema
        private String username;
        @Schema
        private String loginId;
        @Schema
        private String description;
        @Schema
        private String profileUri;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserDeleteDto {
        @Schema
        private Long userid;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserPatchDto {
        @Schema
        private Long userid;
        @Schema
        private String password;
        @Schema
        private String description;
        @Schema
        private MultipartFile profileImage;
    }
}
