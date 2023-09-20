package com.capstone.ai_painter_backen.dto.Message;

import com.capstone.ai_painter_backen.constant.MessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class MessageDto {

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessagePostDto {
        @Schema
        private MessageType type;
        @Schema
        private String content;
        @Schema
        private Long roomEntityId;
        @Schema
        private Long chatUserEntityId;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessageResponseDto {
        @Schema
        private String content;
        @Schema
        private String writer;
        @Schema
        private String createdAt;
        @Schema
        private String profileImage;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessageCacheDto {
        private String content;
        private String createdAt;
        private Long chatUserId;
    }
}
