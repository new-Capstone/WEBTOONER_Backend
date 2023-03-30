package com.capstone.ai_painter_backen.mapper.image;

import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BeforeImageMapper {

    default BeforeImageEntity BeforeImagePostDtoToBeforeImageEntity(BeforeImageDto.PostDto postDto, S3ImageInfo s3ImageInfo){
        return BeforeImageEntity.builder()
                .beforeImageUri(s3ImageInfo.getFileURI())
                .userEntity(null)//유저 정보는 따로 넣어야함.
                .build();

    }
    default AfterImageDto.ResponseDto afterImageEntityToAfterImageResponseDto(AfterImageEntity afterImageEntity){
        return AfterImageDto.ResponseDto.builder()
                .beforeImageId(afterImageEntity.getBeforeImageEntity().getId())
                .afterImageUri(afterImageEntity.getImageURI())
                .afterImageId(afterImageEntity.getId())
                .build();
    }

    default BeforeImageDto.ResponseDto BeforeImageEntityToBeforeImageResponseDto(BeforeImageEntity beforeImageEntity){

        return BeforeImageDto.ResponseDto.builder()
                .afterImageResponseDtos(beforeImageEntity.getAfterImageEntities().stream()
                        .map(this::afterImageEntityToAfterImageResponseDto).collect(Collectors.toList()))//dto 로 변환해서 전송함.
                .beforeImageId(beforeImageEntity.getId())
                .beforeImageUri(beforeImageEntity.getBeforeImageUri())
                .userId(beforeImageEntity.getUserEntity().getId())
                .build();
    }

}
