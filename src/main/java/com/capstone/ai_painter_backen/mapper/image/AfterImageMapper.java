package com.capstone.ai_painter_backen.mapper.image;

import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface AfterImageMapper {

    default AfterImageDto.ResponseDto afterImageEntityToAfterImageDto(AfterImageEntity afterImageEntity){
        return AfterImageDto.ResponseDto.builder()
                .afterImageId(afterImageEntity.getId())
                .afterImageUri(afterImageEntity.getImageURI())
                .beforeImageId(afterImageEntity.getBeforeImageEntity().getId())
                .build();
    }

}
