package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.controller.dto.CategoryDto.RequestSaveDto;
import com.capstone.ai_painter_backen.controller.dto.CategoryDto.ResponseDto;
import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface CategoryMapper {



    CategoryEntity requestSaveDtoToEntity(RequestSaveDto dto);
    ResponseDto entityToResponseDto(CategoryEntity entity);
}
