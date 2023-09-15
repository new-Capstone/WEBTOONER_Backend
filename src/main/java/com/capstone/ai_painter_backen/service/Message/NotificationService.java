package com.capstone.ai_painter_backen.service.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.NotificationEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.NotificationDto;
import com.capstone.ai_painter_backen.dto.UserDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.message.NotificationMapper;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
import com.capstone.ai_painter_backen.repository.message.NotificationRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;
import com.capstone.ai_painter_backen.service.security.SecurityUserInfoService;
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
    private final SecurityUserInfoService securityUserInfoService;

    @Transactional
    public void createNotification(NotificationDto.NotificationPostDto notificationPostDto) {
        RoomEntity savedRoomEntity = roomRepository.findById(notificationPostDto.getRoodEntityId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));
        MessageEntity savedMessageEntity = messageRepository.findById(notificationPostDto.getMessageEntityId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MESSAGE_NOT_FOUND));

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .checked(false)
                .message(savedMessageEntity)
                .build();

        if (savedRoomEntity.getOwner().getId() == notificationPostDto.getChatUserEntityId()) {
            UserEntity savedUserEntity = userRepository.findById(savedRoomEntity.getVisitor().getId()).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
            notificationEntity.setUser(savedUserEntity);
        } else {
            UserEntity savedUserEntity = userRepository.findById(savedRoomEntity.getOwner().getId()).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
            notificationEntity.setUser(savedUserEntity);
        }

        notificationRepository.save(notificationEntity);
    }

    @Transactional
    public List<NotificationDto.NotificationResponseDto> getNotifications() {
        UserDto.CusTomUserPrincipalDto cusTomUserPrincipalDto = securityUserInfoService.getUserInfoFromSecurityContextHolder();
        UserEntity savedUserEntity = userRepository.findByUserEmail(cusTomUserPrincipalDto.getEmail()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        List<NotificationEntity> notificationEntities = notificationRepository.findALlByUserAndChecked(savedUserEntity, false);

        //알림 조회할 때 check
        for (NotificationEntity notificationEntity : notificationEntities) {
            notificationEntity.check();
        }

        return notificationEntities.stream()
                .map(notificationMapper::notificationEntityToNotificationResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteNotification() {
        UserDto.CusTomUserPrincipalDto cusTomUserPrincipalDto = securityUserInfoService.getUserInfoFromSecurityContextHolder();

        UserEntity savedUserEntity = userRepository.findByUserEmail(cusTomUserPrincipalDto.getEmail()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        //조회된 알림 list
        List<NotificationEntity> notificationEntities = notificationRepository.findALlByUserAndChecked(savedUserEntity, true);

        for (NotificationEntity notificationEntity : notificationEntities) {
            notificationRepository.delete(notificationEntity);
        }
    }
}
