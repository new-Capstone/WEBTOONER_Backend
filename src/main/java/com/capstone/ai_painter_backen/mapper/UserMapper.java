package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.constant.Role;
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
                    .userEmail(userPostDto.getUserEmail())
                    .password(userPostDto.getPassword())
                    .profileImage(s3ImageInfo.getFileURI())
                    .userRealName(userPostDto.getUsername())
                    .role(Role.USER)
                    .description(userPostDto.getDescription()).build();
        }
    }

    default UserEntity userRequestPostDtoToUserEntity(UserDto.UserPostDto userPostDto) {
        if (userPostDto == null) {
            return null;
        } else {
            return UserEntity.builder()
                    .userEmail(userPostDto.getUserEmail())
                    .password(userPostDto.getPassword())
                    .userRealName(userPostDto.getUsername())
                    .role(Role.USER)
                    .description(userPostDto.getDescription()).build();
        }
    }
    default UserDto.TeachingInformationDto extractingTeachingInformation(UserEntity user){
        Long tuteeId = null;
        Long tutorId = null;
        if(user.getTuteeEntity() != null){
            tuteeId = user.getTuteeEntity().getId();
        }

        if(user.getTutorEntity() != null){
            tutorId = user.getTutorEntity().getId();
        }
        return UserDto.TeachingInformationDto.builder()
                .tuteeId(tuteeId)
                .tutorId(tutorId)
                .build();
    }

    default UserDto.UserResponseDto userEntityToUserResponseDto(UserEntity userEntity){
        if (userEntity == null) {
            return null;
        } else {
            return UserDto.UserResponseDto.builder()
                    .userId(userEntity.getId())
                    .userEmail(userEntity.getUserEmail())
                    .username(userEntity.getUserRealName())
                    .description(userEntity.getDescription())
                    .profileImage(userEntity.getProfileImage())
                    .teachingInformationDto(extractingTeachingInformation(userEntity))
                    .build();
        }
    }

}

