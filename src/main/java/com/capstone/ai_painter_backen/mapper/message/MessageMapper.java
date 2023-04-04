package com.capstone.ai_painter_backen.mapper.message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.dto.Message.RoomDto.RoomResponseDto;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.dto.UserDto.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    default MessageEntity messageRequestPostDtoToMessageEntity(MessageDto.MessagePostDto postDto) {
        if (postDto == null) {
            return null;
        } else {
            return MessageEntity.builder()
                    .content(postDto.getContent())
                    .build();
        }
    }

    default UserResponseDto userEntityToUserResponseDto(UserEntity userEntity) {
        return UserResponseDto.builder()
                .description(userEntity.getDescription())
                .loginId(userEntity.getLoginId())
                .userid(userEntity.getId())
                .username(userEntity.getUsername())
                .profileImage(userEntity.getProfileImage())
                .build();
    }

    default MessageDto.MessageResponseDto messageEntityToMessageResponseDto(MessageEntity messageEntity){
        if (messageEntity == null) {
            return null;
        } else {
            return MessageDto.MessageResponseDto.builder()
                    .messageId(messageEntity.getId())
                    .chatUser(userEntityToUserResponseDto(messageEntity.getChatUserEntity()))
                    .content(messageEntity.getContent())
                    .roomId(messageEntity.getId())
                    .build();
        }
    }

}
