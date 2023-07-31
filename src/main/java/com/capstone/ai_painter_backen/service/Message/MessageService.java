package com.capstone.ai_painter_backen.service.Message;


import com.capstone.ai_painter_backen.constant.MessageType;
import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.dto.Message.NotificationDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.message.MessageMapper;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;
import com.capstone.ai_painter_backen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.usertype.BaseUserTypeSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate simpMessagingTemplate;
    //어차피 단방향 의존이라 Service로 받았음.
    private final NotificationService notificationService;


    @Transactional
    public MessageDto.MessageResponseDto createMessage(MessageDto.MessagePostDto postDto){

        UserEntity userEntity = userRepository.findById(postDto.getChatUserEntityId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        RoomEntity roomEntity = roomRepository.findRoomByUserId(
                postDto.getChatUserEntityId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_AT_ROOM));

        if (roomEntity.getId() != postDto.getRoomEntityId()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_AT_ROOM);
        }

        postDto.setWriter(userEntity.getUserRealName());

        if (postDto.getType() == MessageType.ENTER) {
            postDto.setContent(postDto.getWriter() + "님이 입장했습니다.");
        }

        MessageEntity messageEntity = messageMapper.messageRequestPostDtoToMessageEntity(postDto);

        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + Long.toString(postDto.getRoomEntityId()), postDto);

        messageEntity.addRoomEntity(roomEntity);
        messageEntity.setChatUserEntity(userEntity);

        MessageEntity savedMessage = messageRepository.save(messageEntity);

        NotificationDto.NotificationPostDto notificationPostDto = NotificationDto.NotificationPostDto.builder()
                .chatUserEntityId(postDto.getChatUserEntityId())
                .roodEntityId(postDto.getRoomEntityId())
                .content(postDto.getContent())
                .messageEntityId(savedMessage.getId()).build();

        notificationService.createNotification(notificationPostDto);

        return messageMapper.messageEntityToMessageResponseDto(savedMessage);
    }

    public Page<MessageDto.MessageResponseDto> getMessages(Pageable pageable, Long roomId) {
        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));
        Page<MessageEntity> messageEntities = messageRepository.findAllByRoomIdWithRoomAndUser(roomId, pageable);
        List<MessageDto.MessageResponseDto> messageResponseDtos = messageEntities.stream()
                .map(messageMapper::messageEntityToMessageResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(messageResponseDtos, messageEntities.getPageable(), messageEntities.getNumber());
    }
}
