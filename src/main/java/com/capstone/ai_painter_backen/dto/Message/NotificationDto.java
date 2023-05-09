package com.capstone.ai_painter_backen.dto.Message;


import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class NotificationDto {

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NotificationPostDto {
        @Schema
        private Long chatUserEntityId;
        @Schema
        private Long roodEntityId;
        @Schema
        private Long messageEntityId;
        @Schema
        private String content;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class NotificationResponseDto {
        @Schema
        private Long NotificationId;
        @Schema
        private MessageDto.MessageResponseDto messageResponseDto;
        @Schema
        private Long userId;
    }
}
