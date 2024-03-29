package com.capstone.ai_painter_backen.dto.mentor;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
public class TuteeDto {
    @Schema
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class TuteeRequestSaveDto {
        @NotNull(message = "유저 ID는 필수값입니다.")
        Long userId;
        @NotNull(message = "튜터 ID는 필수값입니다.")
        Long tutorId;
    }
    @Schema
    @Data
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class TuteeRequestUpdateDto {

        @Schema
        @NotNull(message = "튜티 ID는 필수입니다.")
        Long id; // tutee id

        @Schema
        @NotNull(message = "튜터 ID는 필수값입니다.")
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
