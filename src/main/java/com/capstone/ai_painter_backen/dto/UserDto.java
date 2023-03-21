package com.capstone.ai_painter_backen.dto;

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
        private String loginId;
    }



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PatchDto {
        private String loginId;
        private String username;
        private String password;
        private String description;
        private String profileImage;
    }
}
