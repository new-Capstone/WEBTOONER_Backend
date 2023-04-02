package com.capstone.ai_painter_backen.dto.mentor;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
@Getter
public class TuteeDto {
    @Schema
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class TuteeRequestSaveDto {
         Long userId;
         Long tutorId;
    }
    @Schema
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class TuteeRequestUpdateDto {
        // TODO :: dto 로 변경
        // user 는 수정 불가
        @Schema
        Long id; // tutee id
        @Schema
        Long tutorId;
    }
    @Schema
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class TuteeRequestDeleteDto {
        Long tuteeId;

        @Override
        public String toString(){
            return tuteeId + "deleted !!";
        }
    }
    @Schema
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class TuteeResponseDto {
        @Schema
         Long tuteeId;
        @Schema
        String tuteeName;
        @Schema
        Long tutorId;
        @Schema
        String tutorName; //튜터 이름

    }
}
