package com.capstone.ai_painter_backen.dto.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.RoomDto.RoomResponseDto;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.dto.UserDto.UserResponseDto;
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
        private String content;
        @Schema
        private String writer;
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
        private Long messageId;
        @Schema
        private String content;
        @Schema
        private Long roomId;
        @Schema
        private Long chatUserId;
        @Schema
        private String createdAt;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessageDeleteDto {
        @Schema
        private Long messageId;
    }

//    메세지를 수정하지는 않을듯.
//    public static class PatchDto {
//    }
}
