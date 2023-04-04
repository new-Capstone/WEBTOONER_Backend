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
        private String content;
        private String writer;
        private Long roomEntityId;
        private Long chatUserEntityId;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessageResponseDto {
        private Long messageId;

        private String content;

        private Long roomId;

        private UserResponseDto chatUser;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MessageDeleteDto {
        private Long messageId;
    }

//    메세지를 수정하지는 않을듯.
//    public static class PatchDto {
//    }
}
