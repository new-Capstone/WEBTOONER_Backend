package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.TutorDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface TutorMapper {
    default TutorEntity tutorRequestPostDtoToTutorEntity(TutorDto.PostDto postDto){
        return TutorEntity.builder()
                .description(postDto.getDescription())
                .tuteeEntities(new ArrayList<>())
                .categoryTutorEntities(new ArrayList<>()) // todo 나중에 문자열을 카테고리고 변환하는 mapper 필요
                .build();
    }

    default  TutorDto.ResponseDto tutorEntityToTutorResponseDto(TutorEntity tutorEntity){
        return TutorDto.ResponseDto.builder()
                .categoryTutorEntities(tutorEntity.getCategoryTutorEntities())
                .tuteeEntities(tutorEntity.getTuteeEntities())
                .tutorId(tutorEntity.getId())
                .description(tutorEntity.getDescription())
                .build();
    }

}
