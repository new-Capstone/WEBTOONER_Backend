package com.capstone.ai_painter_backen.dto.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import lombok.*;

public class MessageDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostDto {
        private String content;

        private Long roomEntityId;

        private Long chatUserEntityId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDto {
        private Long messageId;

        private String content;

        private RoomEntity roomEntity;

        private UserEntity chatUserEntity;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DeleteDto {
        private Long messageId;
    }

//    메세지를 수정하지는 않을듯.
//    public static class PatchDto {
//    }
}
