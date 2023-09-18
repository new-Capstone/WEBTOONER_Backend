package com.capstone.ai_painter_backen.service.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.NotificationEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.message.RoomMapper;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
import com.capstone.ai_painter_backen.repository.message.NotificationRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;

import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.service.security.SecurityUserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


//userentity -> room 단방향. 연관관계의 주인은 room
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final MessageRepository messageRepository;
    private final SecurityUserInfoService securityUserInfoService;

    @Transactional
    public RoomDto.RoomResponseDto createRoom(RoomDto.RoomPostDto postDto) {
        UserEntity userEntity = securityUserInfoService.getLoginUser();

        RoomEntity roomEntity = roomMapper.roomRequestPostDtoToRoomEntity(postDto);

        roomEntity.setOwner(userEntity);
        roomEntity.setVisitor(userRepository.findById(postDto.getVisitorId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)));

        return roomMapper.roomEntityToRoomResponseDto(roomRepository.save(roomEntity));
    }

    public RoomDto.RoomResponseDto getRoom(Long roomId) {
        return roomMapper.roomEntityToRoomResponseDto(roomRepository.findById(roomId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND)));
    }

    public List<RoomDto.RoomResponseDto> getRooms() {
        UserEntity userEntity = securityUserInfoService.getLoginUser();

        List<RoomEntity> roomEntities = roomRepository.findAllByOwnerOrVisitor(userEntity, userEntity);

       return roomEntities.stream().map(roomMapper::roomEntityToRoomResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteRoom(RoomDto.RoomDeleteDto deleteDto) {
        if (!roomRepository.existsById(deleteDto.getRoomId())) {
            throw new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND);
        }

        List<MessageEntity> messageEntities = messageRepository.findAllByRoomId(deleteDto.getRoomId());

        for (MessageEntity messageEntity : messageEntities) {
            Optional<NotificationEntity> notificationEntity = notificationRepository.findByMessage(messageEntity);
            if (notificationEntity.isPresent()) {
                notificationRepository.delete(notificationEntity.get());
            }
        }

        roomRepository.deleteById(deleteDto.getRoomId());
        log.info("{}: Room 삭제됨 !", deleteDto.getRoomId());
    }
}
