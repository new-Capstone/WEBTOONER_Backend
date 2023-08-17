package com.capstone.ai_painter_backen.mapper.mentor;

import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto;
import org.mapstruct.Mapper;

import static com.capstone.ai_painter_backen.dto.mentor.TuteeDto.*;

@Mapper(componentModel = "spring")
public interface TuteeMapper {

    default TuteeDto.TuteeResponseDto entityToTuteeResponseDto(TuteeEntity tutee){
        String tutorName;
        if((tutorName = tutee.getTutorEntity().getUserEntity().getUserRealName()) == null){
            tutorName = "튜터 이름을 등록해주세요";
        }

        return TuteeResponseDto.builder()
                .tuteeId(tutee.getId())
                .tuteeName(tutee.getUserEntity().getUserRealName())
                .tutorId(tutee.getTutorEntity().getId())
                .tutorName(tutorName)
                .build();
    }
}
