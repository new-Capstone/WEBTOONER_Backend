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

import java.text.SimpleDateFormat;

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

    default MessageDto.MessageResponseDto messageEntityToMessageResponseDto(MessageEntity messageEntity){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (messageEntity == null) {
            return null;
        } else {
            return MessageDto.MessageResponseDto.builder()
                    .messageId(messageEntity.getId())
                    .chatUserId(messageEntity.getChatUserEntity().getId())
                    .content(messageEntity.getContent())
                    .createdAt(format.format(messageEntity.getRegisteredTime()))
                    .roomId(messageEntity.getId()).build();
        }
    }

}
