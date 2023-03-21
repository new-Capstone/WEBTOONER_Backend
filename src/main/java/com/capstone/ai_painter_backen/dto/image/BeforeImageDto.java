package com.capstone.ai_painter_backen.dto.image;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class BeforeImageDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class ResponseDto{
        Long beforeImageId;
        Long userId;
        String beforeImageUri;
        List<AfterImageDto.ResponseDto> afterImageresponseDtos = new ArrayList<>();
    }
}
