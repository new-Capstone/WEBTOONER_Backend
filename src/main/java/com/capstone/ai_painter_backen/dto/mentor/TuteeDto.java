package com.capstone.ai_painter_backen.dto.mentor;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TuteeDto {

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class RequestSaveDto {
        private Long userId;
        private Long tutorId;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class RequestUpdateDto {
        // TODO :: dto 로 변경
        // user 는 수정 불가
        private TutorEntity tutor;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class RequestDeleteDto {
        private Long tuteeId;
    }

    @Getter
    @NoArgsConstructor
    @Builder
    @AllArgsConstructor
    public static class ResponseDto {
        private Long tuteeId;
        // TODO : UserDto 로 변경
        private UserEntity userEntity;
        // TODO : TutorDto 로 변경
        private TutorEntity tutorEntity;
    }
}
