package com.capstone.ai_painter_backen.mapper.image;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BeforeImageMapper {

    default BeforeImageEntity BeforeImagePostDtoToBeforeImageEntity(BeforeImageDto.BeforeImagePostDto beforeImagePostDto, S3ImageInfo s3ImageInfo, UserEntity userEntity) {
        return BeforeImageEntity.builder()
                .beforeImageUri(s3ImageInfo.getFileURI())
                .userEntity(userEntity)//유저 정보는 따로 넣어야함.
                .afterImageEntities(new ArrayList<>())//처음 초기화를 해서 null point exception 방지함.
                .build();

    }

    default AfterImageDto.AfterImageResponseDto afterImageEntityToAfterImageResponseDto(AfterImageEntity afterImageEntity) {
        return AfterImageDto.AfterImageResponseDto.builder()
                .beforeImageId(afterImageEntity.getBeforeImageEntity().getId())
                .afterImageUri(afterImageEntity.getImageURI())
                .afterImageId(afterImageEntity.getId())
                .build();
    }

    default BeforeImageDto.BeforeImageResponseDto BeforeImageEntityToBeforeImageResponseDto(BeforeImageEntity beforeImageEntity) {

        return BeforeImageDto.BeforeImageResponseDto.builder()
                .afterImageAfterImageResponseDtos(beforeImageEntity.getAfterImageEntities().stream()
                        .map(this::afterImageEntityToAfterImageResponseDto).collect(Collectors.toList()))//dto 로 변환해서 전송함.
                .beforeImageId(beforeImageEntity.getId())
                .beforeImageUri(beforeImageEntity.getBeforeImageUri())
                .userId(beforeImageEntity.getUserEntity().getId())
                .build();
    }

    default BeforeImageDto.BeforeImageCreateResponseDto BeforeImageEntityToBeforeImageCreateResponseDto(
            BeforeImageEntity beforeImageEntity, List<MultipartFile> transformedImages) {
        return BeforeImageDto.BeforeImageCreateResponseDto.builder()
                .createdImages(transformedImages)
                .beforeImageId(beforeImageEntity.getId())
                .beforeImageUri(beforeImageEntity.getBeforeImageUri())
                .userId(beforeImageEntity.getUserEntity().getId())
                .build();
    }
}

