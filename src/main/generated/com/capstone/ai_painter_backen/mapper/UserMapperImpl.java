package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.dto.UserDto;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-23T13:11:18+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserEntity userRequestPostDtoToUserEntity(UserDto.PostDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        return userEntity;
    }

    @Override
    public UserDto.ResponseDto userEntityToUserResponseDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDto.ResponseDto.ResponseDtoBuilder responseDto = UserDto.ResponseDto.builder();

        responseDto.username( userEntity.getUsername() );
        responseDto.loginId( userEntity.getLoginId() );
        responseDto.description( userEntity.getDescription() );
        responseDto.profileImage( userEntity.getProfileImage() );

        return responseDto.build();
    }
}
