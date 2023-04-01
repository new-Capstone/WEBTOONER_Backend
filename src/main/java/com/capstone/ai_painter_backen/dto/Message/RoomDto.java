package com.capstone.ai_painter_backen.dto.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;

import lombok.*;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

public class RoomDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostDto {
        private Long ownerId;
        private Long visitorId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ResponseDto {
        private Long roomid;
        private UserEntity owner;
        private UserEntity visitor;
        private List<MessageEntity> messageEntities = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DeleteDto {
        private Long roomid;
    }

}
