package com.capstone.ai_painter_backen.mapper.etc;

import com.capstone.ai_painter_backen.dto.mentor.CategoryDto.RequestSaveDto;
import com.capstone.ai_painter_backen.dto.mentor.CategoryDto.ResponseDto;
import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    default CategoryEntity requestSaveDtoToEntity(RequestSaveDto dto) {
        return CategoryEntity.builder()
                .categoryName(dto.getCategoryName())
                .categoryTutorEntities(new ArrayList<>())
                .build();
    }

    default ResponseDto entityToResponseDto(CategoryEntity entity) {
        return ResponseDto.builder()
                .id(entity.getId())
                .categoryName(entity.getCategoryName())
                .categoryTutorEntities(entity.getCategoryTutorEntities())
                .build();
    }
}
