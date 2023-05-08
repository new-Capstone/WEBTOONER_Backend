package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //    @Mapping(target = "id", ignore = true)
    default UserEntity userRequestPostDtoToUserEntity(UserDto.UserPostDto userpostDto) {
        if (userpostDto == null) {
            return null;
        } else {
            return UserEntity.builder()
                    .userEmail(userpostDto.getUserEmail())
                    .password(userpostDto.getPassword())
                    .profileImage(userpostDto.getProfileImage())
                    .userRealName(userpostDto.getUsername())
                    .description(userpostDto.getDescription()).build();
        }
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
                    .build();
        }
    }

}

