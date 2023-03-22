package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import org.mapstruct.Mapper;

import static com.capstone.ai_painter_backen.controller.dto.TuteeDto.*;

@Mapper(componentModel = "spring")
public interface TuteeMapper {
    default ResponseDto entityToResponseDto(TuteeEntity tutee) {
        return ResponseDto.builder()
                .tuteeId(tutee.getId())
                .userEntity(tutee.getUserEntity())
                .tutorEntity(tutee.getTutorEntity())
                .build();
    }
}
