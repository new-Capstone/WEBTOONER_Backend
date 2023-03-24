package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.dto.S3ImageInfo;
import com.capstone.ai_painter_backen.dto.image.BeforeImageDto;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface BeforeImageMapper {

    default BeforeImageEntity BeforeImagePostDtoToBeforeImageEntity(BeforeImageDto.PostDto postDto, S3ImageInfo s3ImageInfo){
        return BeforeImageEntity.builder()
                .beforeImageUri(s3ImageInfo.getFileURI())
                .userEntity(null)//유저 정보는 따로 넣어야함.
                .build();

    }
    default BeforeImageDto.ResponseDto BeforeImageEntityToBeforeImageResponseDto(BeforeImageEntity beforeImageEntity){

        return BeforeImageDto.ResponseDto.builder()
                .afterImageResponseDtos(new ArrayList<>())
                .beforeImageId(beforeImageEntity.getId())
                .beforeImageUri(beforeImageEntity.getBeforeImageUri())
//                .userId(beforeImageEntity.getUserEntity().getId())
                .build();
    }
}
