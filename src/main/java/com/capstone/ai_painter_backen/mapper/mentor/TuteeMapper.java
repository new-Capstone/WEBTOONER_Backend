package com.capstone.ai_painter_backen.mapper.mentor;

import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto;
import org.mapstruct.Mapper;

import static com.capstone.ai_painter_backen.dto.mentor.TuteeDto.*;

@Mapper(componentModel = "spring")
public interface TuteeMapper {

    default TuteeDto.TuteeResponseDto entityToTuteeResponseDto(TuteeEntity tutee){
        return TuteeResponseDto.builder()
                .tuteeId(tutee.getId())
                .tuteeName(tutee.getUserEntity().getUsername())
                .tutorId(tutee.getTutorEntity().getId())
                .tutorName(tutee.getTutorEntity().getUserEntity().getUsername())
                .build();
    }
}
