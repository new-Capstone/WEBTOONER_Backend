package com.capstone.ai_painter_backen.dto;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class RoomDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PostDto {
        private UserEntity owner;
        private UserEntity visitor;
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


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PatchDto {
        private Long roomid;
        private List<MessageEntity> messageEntities = new ArrayList<>();
    }
}
