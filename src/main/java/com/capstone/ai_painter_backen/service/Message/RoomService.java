package com.capstone.ai_painter_backen.service.Message;

import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.mapper.message.RoomMapper;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;

import com.capstone.ai_painter_backen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//userentity -> room 단방향. 연관관계의 주인은 room
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomMapper roomMapper;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

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

    @Transactional
    public void deleteRoom(RoomDto.RoomDeleteDto deleteDto) {
        roomRepository.deleteById(deleteDto.getRoomId());
        log.info("{}: Room 삭제됨 !", deleteDto.getRoomId());
    }

}
