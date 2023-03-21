package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface BeforeImageMapper {

    default BeforeImageEntity MultipartFileToBeforeImageEntity(MultipartFile multipartFile){
        return BeforeImageEntity.builder()
                .beforeImageUri(multipartFile.getOriginalFilename())
                .build();
    }
    default BeforeImageDto.ResponseDto BeforeImageEntityToBeforeImageResponseDto(BeforeImageEntity beforeImageEntity){

        return BeforeImageDto.ResponseDto.builder()
                .afterImageresponseDtos(new ArrayList<>())
                .beforeImageId(beforeImageEntity.getId())
                .beforeImageUri(beforeImageEntity.getBeforeImageUri())
                .userId(beforeImageEntity.getUserEntity().getId())
                .build();
    }
}
