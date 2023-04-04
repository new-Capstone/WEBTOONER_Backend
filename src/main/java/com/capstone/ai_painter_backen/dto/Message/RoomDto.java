package com.capstone.ai_painter_backen.dto.Message;


import io.swagger.v3.oas.annotations.media.Schema;
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
        private Long roomId;
    }
}
