package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    //    @Mapping(target = "id", ignore = true)
    default UserEntity userRequestPostDtoToUserEntity(UserDto.PostDto userpostDto) {
        if (userpostDto == null) {
            return null;
        } else {
            return UserEntity.builder()
                    .loginId(userpostDto.getLoginId())
                    .password(userpostDto.getPassword())
                    .profileImage(userpostDto.getProfileImage())
                    .username(userpostDto.getUsername())
                    .description(userpostDto.getDescription()).build();
        }
    }

    default UserDto.ResponseDto userEntityToUserResponseDto(UserEntity userEntity){
        if (userEntity == null) {
            return null;
        } else {
            return UserDto.ResponseDto.builder()
                    .userid(userEntity.getId())
                    .loginId(userEntity.getLoginId())
                    .username(userEntity.getUsername())
                    .description(userEntity.getDescription())
                    .profileImage(userEntity.getProfileImage())
                    .build();
        }
    }

}

