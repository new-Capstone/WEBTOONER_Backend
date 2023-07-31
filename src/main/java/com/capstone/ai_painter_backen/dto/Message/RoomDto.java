package com.capstone.ai_painter_backen.dto.Message;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

public class RoomDto {

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RoomPostDto {
        @Schema
        @NotNull(message = "채팅방 생성 유저 아이디는 필수값입니다.")
        private Long ownerId;
        @Schema
        @NotNull(message = "채팅방 참여 유저 아이디는 필수값입니다.")
        private Long visitorId;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RoomResponseDto {
        @Schema
        private Long roomId;
        @Schema
        private Long ownerId;
        @Schema
        private Long visitorId;
    }

    @Schema
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class RoomDeleteDto {
        @Schema
        @NotNull(message = "채팅방 아이디는 필수값입니다.")
        private Long roomId;
    }
}
