package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.repository.MessageRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    default MessageEntity messageRequestPostDtoToMessageEntity(MessageDto.PostDto postDto) {
        if (postDto == null) {
            return null;
        } else {
            return MessageEntity.builder()
                    .content(postDto.getContent())
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
