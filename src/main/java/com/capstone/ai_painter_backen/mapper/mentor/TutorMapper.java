package com.capstone.ai_painter_backen.mapper.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import com.capstone.ai_painter_backen.domain.mentor.TuteeEntity;
import com.capstone.ai_painter_backen.domain.mentor.TutorEntity;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto;
import com.capstone.ai_painter_backen.dto.mentor.TutorDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TutorMapper {
    default TutorEntity tutorRequestPostDtoToTutorEntity(TutorDto.TutorPostDto tutorPostDto){
        return TutorEntity.builder()
                .description(tutorPostDto.getDescription())
                .tuteeEntities(new ArrayList<>())
                .categoryTutorEntities(new ArrayList<>()) // todo 나중에 문자열을 카테고리고 변환하는 mapper 필요
                .tutorEmail(tutorPostDto.getTutorEmail())
                .tutorName(tutorPostDto.getTutorName())
                .build();
    }

    default String categoryTutorEntityToCategoryName(CategoryTutorEntity categoryTutorEntity){
        return categoryTutorEntity.getCategoryEntity().getCategoryName();
    }
    default TuteeDto.TuteeResponseDto entityToTuteeResponseDtoAtTutor(TuteeEntity tutee){
        return TuteeDto.TuteeResponseDto.builder()
                .tuteeId(tutee.getId())
                .tuteeName(tutee.getUserEntity().getUserEmail())//유저 이메일
                .build();
    }

    default TutorDto.TutorResponseDto tutorEntityToTutorResponseDto(TutorEntity tutorEntity){
        return TutorDto.TutorResponseDto.builder()
                .categoryNames(tutorEntity.getCategoryTutorEntities().stream()
                        .map(this::categoryTutorEntityToCategoryName).collect(Collectors.toList()))
                .tuteeResponseDtos(tutorEntity.getTuteeEntities().stream().map(this::entityToTuteeResponseDtoAtTutor)
                        .collect(Collectors.toList()))
                .tutorId(tutorEntity.getId())
                .description(tutorEntity.getDescription())
                .tutorEmail(tutorEntity.getTutorEmail())
                .tutorName(tutorEntity.getTutorName())
                .build();
    }

    default TutorDto.TutorResponseDtoIdAndImage tutorEntityToTutorResponseDtoIdAndImage(TutorEntity tutorEntity) {
        try {
            return TutorDto.TutorResponseDtoIdAndImage.builder()
                    .tutorId(tutorEntity.getId())
                    .url(tutorEntity.getPortfolioEntities().get(0).getImageUri())
                    .build();
        } catch (Exception e){
            return null;
        }
    }
}
