package com.capstone.ai_painter_backen.service;

import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.MessageDto;
import com.capstone.ai_painter_backen.dto.RoomDto;
import com.capstone.ai_painter_backen.mapper.MessageMapper;
import com.capstone.ai_painter_backen.mapper.RoomMapper;
import com.capstone.ai_painter_backen.repository.MessageRepository;
import com.capstone.ai_painter_backen.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


//userentity -> room 단방향. 연관관계의 주인은 room
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final RoomMapper roomMapper;
    private final MessageMapper messageMapper;
    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;
    private final MessageEntity messageEntity;

    public RoomDto.ResponseDto createRoom(RoomDto.PostDto postDto){
        RoomEntity roomEntity = roomMapper.roomRequestPostDtoToRoomEntity(postDto);
        return roomMapper.roomEntityToRoomResponseDto(roomRepository.save(roomEntity));
    }

    public MessageDto.ResponseDto sendChat(MessageDto.PostDto postDto){
        MessageEntity message = messageEntity.createMessage(postDto.getContent(),
                postDto.getRoomEntity(), postDto.getChatUserEntity());
        return messageMapper.messageEntityToMessageResponseDto(messageRepository.save(message));
    }

    public RoomDto.ResponseDto getRoom(Long roomId){
        return roomMapper.roomEntityToRoomResponseDto(roomRepository.findById(roomId).orElseThrow());
    }

    public void deleteRoom(RoomDto.DeleteDto deleteDto){
        roomRepository.deleteById(deleteDto.getRoomid());
        log.info("{}: Room 삭제됨 !", deleteDto.getRoomid());
    }
    public void deleteChat(MessageDto.DeleteDto deleteDto){
        messageRepository.deleteById(deleteDto.getMessageId());
        log.info("{}: Message 삭제됨 !", deleteDto.getMessageId());
    }

}
