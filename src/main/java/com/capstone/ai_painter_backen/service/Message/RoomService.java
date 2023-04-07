package com.capstone.ai_painter_backen.service.Message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.NotificationEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.mapper.message.RoomMapper;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
import com.capstone.ai_painter_backen.repository.message.NotificationRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;

import com.capstone.ai_painter_backen.repository.UserRepository;
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
    private final MessageRepository messageRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    public RoomDto.RoomResponseDto createRoom(RoomDto.RoomPostDto postDto) {
        RoomEntity roomEntity = roomMapper.roomRequestPostDtoToRoomEntity(postDto);
        roomEntity.setOwner(userRepository.findById(postDto.getOwnerId()).orElseThrow());
        roomEntity.setVisitor(userRepository.findById(postDto.getVisitorId()).orElseThrow());

        return roomMapper.roomEntityToRoomResponseDto(roomRepository.save(roomEntity));
    }

    public RoomDto.RoomResponseDto getRoom(Long roomId) {
        return roomMapper.roomEntityToRoomResponseDto(roomRepository.findById(roomId).orElseThrow());
    }

    public List<RoomDto.RoomResponseDto> getRoomsByUserId(Long userId) {
        UserEntity savedUserEntity = userRepository.findById(userId).orElseThrow();
        List<RoomEntity> roomEntities = roomRepository.findAllByOwnerOrVisitor(savedUserEntity, savedUserEntity);

       return roomEntities.stream().map(roomMapper::roomEntityToRoomResponseDto).collect(Collectors.toList());
    }

    @Transactional
    public void deleteRoom(RoomDto.RoomDeleteDto deleteDto) {
        //Room 삭제할 때 message 같이 삭제
        RoomEntity savedRoomEntity = roomRepository.findById(deleteDto.getRoomId()).orElseThrow();
        List<MessageEntity> messageEntities = savedRoomEntity.getMessageEntities();

        for (MessageEntity messageEntity : messageEntities) {
            NotificationEntity savedNotificationEntity = notificationRepository.findByMessage(messageEntity);
            notificationRepository.delete(savedNotificationEntity);
            messageRepository.delete(messageEntity);
        }

        roomRepository.deleteById(deleteDto.getRoomId());
        log.info("{}: Room 삭제됨 !", deleteDto.getRoomId());
    }
}
