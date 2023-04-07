package com.capstone.ai_painter_backen.service.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.NotificationEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.NotificationDto;
import com.capstone.ai_painter_backen.mapper.message.NotificationMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
import com.capstone.ai_painter_backen.repository.message.NotificationRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final NotificationMapper notificationMapper;

    @Transactional
    public void createNotification(NotificationDto.NotificationPostDto notificationPostDto) {
        RoomEntity savedRoomEntity = roomRepository.findById(notificationPostDto.getRoodEntityId()).orElseThrow();
        MessageEntity savedMessageEntity = messageRepository.findById(notificationPostDto.getMessageEntityId()).orElseThrow();

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .checked(false)
                .message(savedMessageEntity)
                .build();

        if (savedRoomEntity.getOwner().getId() == notificationPostDto.getChatUserEntityId()) {
            UserEntity savedUserEntity = userRepository.findById(savedRoomEntity.getVisitor().getId()).orElseThrow();
            notificationEntity.setUser(savedUserEntity);
        } else {
            UserEntity savedUserEntity = userRepository.findById(savedRoomEntity.getOwner().getId()).orElseThrow();
            notificationEntity.setUser(savedUserEntity);
        }

        notificationRepository.save(notificationEntity);
    }

    @Transactional
    public List<NotificationDto.NotificationResponseDto> getNotificationsByUserId(Long userId) {
        UserEntity savedUserEntity = userRepository.findById(userId).orElseThrow();
        List<NotificationEntity> notificationEntities = notificationRepository.findALlByUserAndChecked(savedUserEntity, false);

        //알림 조회할 때 check
        for (NotificationEntity notificationEntity : notificationEntities) {
            notificationEntity.check();
        }

        return notificationEntities.stream()
                .map(notificationMapper::notificationEntityToNotificationResponseDto).collect(Collectors.toList());
    }
}
