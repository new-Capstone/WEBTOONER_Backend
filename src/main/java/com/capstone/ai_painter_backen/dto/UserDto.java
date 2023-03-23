package com.capstone.ai_painter_backen.dto;

import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import lombok.*;

public class UserDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostDto {
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
