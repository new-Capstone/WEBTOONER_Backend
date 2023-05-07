package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.dto.image.S3ImageInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //    @Mapping(target = "id", ignore = true)
    default UserEntity userRequestPostDtoToUserEntity(UserDto.UserPostDto userPostDto, S3ImageInfo s3ImageInfo) {
        if (userPostDto == null) {
            return null;
        } else {
            return UserEntity.builder()
                    .loginId(userPostDto.getLoginId())
                    .password(userPostDto.getPassword())
                    .username(userPostDto.getUsername())
                    .profileUri(s3ImageInfo.getFileURI())
                    .description(userPostDto.getDescription()).build();
        }
    }

    default UserDto.UserResponseDto userEntityToUserResponseDto(UserEntity userEntity){
        if (userEntity == null) {
            return null;
        } else {
            return UserDto.UserResponseDto.builder()
                    .userid(userEntity.getId())
                    .loginId(userEntity.getLoginId())
                    .username(userEntity.getUsername())
                    .description(userEntity.getDescription())
                    .profileUri(userEntity.getProfileUri())
                    .build();
        }
    }

}

