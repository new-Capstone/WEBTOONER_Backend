package com.capstone.ai_painter_backen.service.Message;


import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.message.MessageMapper;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;
import com.capstone.ai_painter_backen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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



    @Transactional
    public MessageDto.MessageResponseDto createMessage(MessageDto.MessagePostDto postDto){
        MessageEntity messageEntity = messageMapper.messageRequestPostDtoToMessageEntity(postDto);

        RoomEntity roomEntity = roomRepository.findById(postDto.getRoomEntityId()).orElseThrow();
        UserEntity userEntity = userRepository.findById(postDto.getChatUserEntityId()).orElseThrow();

        messageEntity.addRoomEntity(roomEntity);
        messageEntity.setChatUserEntity(userEntity);

        return messageMapper.messageEntityToMessageResponseDto(messageRepository.save(messageEntity));
    }

    //사용X, 나중에 메시지 수정 기능 추가하면 그때 수정
    @Transactional
    public void deleteMessage(MessageDto.MessageDeleteDto deleteDto){
        messageRepository.deleteById(deleteDto.getMessageId());
        log.info("{}: Message 삭제됨 !", deleteDto.getMessageId());
    }

    public MessageDto.MessageResponseDto getMessage(Long messageId){
        return messageMapper.messageEntityToMessageResponseDto(messageRepository.findById(messageId).orElseThrow());
    }

    public List<MessageDto.MessageResponseDto> getMessages(Long roomId) {
        RoomEntity roomEntity = roomRepository.findById(roomId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));

        //ORDER BY로 조회해야 함. 일단 합치고 나서 수정할 예정
        List<MessageEntity> messages = messageRepository.findAllByRoomEntity(roomEntity);

        return messages.stream()
                .map(messageMapper::messageEntityToMessageResponseDto)
                .collect(Collectors.toList());
    }
}
