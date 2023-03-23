package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.dto.MessageDto;
import org.mapstruct.Mapper;

import java.util.ArrayList;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    default MessageEntity roomRequestPostDtoToRoomEntity(MessageDto.PostDto roompostDto) {
        if (roompostDto == null) {
            return null;
        } else {
            return MessageEntity.builder()
                    .content(roompostDto.getContent())
                    .roomEntity(roompostDto.getRoomEntity())
                    .chatUserEntity(roompostDto.getChatUserEntity())
                    .build();
        }
    }
    default MessageDto.ResponseDto messageEntityToMessageResponseDto(MessageEntity messageEntity){
        if (messageEntity == null) {
            return null;
        } else {
            return MessageDto.ResponseDto.builder()
                    .messageId(messageEntity.getId())
                    .chatUserEntity(messageEntity.getChatUserEntity())
                    .roomEntity(messageEntity.getRoomEntity())
                    .content(messageEntity.getContent())
                    .build();
        }
    }

}
