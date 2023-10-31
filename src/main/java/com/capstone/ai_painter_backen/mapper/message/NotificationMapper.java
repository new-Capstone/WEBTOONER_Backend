package com.capstone.ai_painter_backen.mapper.message;

import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.NotificationEntity;
import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.dto.Message.NotificationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    /*
    default NotificationDto.NotificationResponseDto notificationEntityToNotificationResponseDto(NotificationEntity notificationEntity) {
        return NotificationDto.NotificationResponseDto.builder()
                .NotificationId(notificationEntity.getId())
                .messageResponseDto(messageEntityToMessageResponseDto(notificationEntity.getMessage()))
                .userId(notificationEntity.getUser().getId()).build();
    }
    */

    /*
    default MessageDto.MessageResponseDto messageEntityToMessageResponseDto(MessageEntity messageEntity) {
        return MessageDto.MessageResponseDto.builder()
                .messageId(messageEntity.getId())
                .chatUserId(messageEntity.getChatUserEntity().getId())
                .roomId(messageEntity.getRoomEntity().getId())
                .content(messageEntity.getContent())
                .build();
    }
    */

}
