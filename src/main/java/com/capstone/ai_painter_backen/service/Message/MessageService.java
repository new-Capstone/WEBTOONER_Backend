package com.capstone.ai_painter_backen.service.Message;


import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.mapper.message.MessageMapper;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;
import com.capstone.ai_painter_backen.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public MessageDto.ResponseDto createMessage(MessageDto.PostDto postDto){
        MessageEntity messageEntity = messageMapper.messageRequestPostDtoToMessageEntity(postDto);
        messageEntity.setRoomEntity(roomRepository.findById(postDto.getRoomEntityId()).orElseThrow());
        messageEntity.setChatUserEntity(userRepository.findById(postDto.getChatUserEntityId()).orElseThrow());
        return messageMapper.messageEntityToMessageResponseDto(messageRepository.save(messageEntity));
    }

    @Transactional
    public void deleteMessage(MessageDto.DeleteDto deleteDto){
        messageRepository.deleteById(deleteDto.getMessageId());
        log.info("{}: Message 삭제됨 !", deleteDto.getMessageId());
    }

    public MessageDto.ResponseDto getMessage(Long messageId){
        return messageMapper.messageEntityToMessageResponseDto(messageRepository.findById(messageId).orElseThrow());
    }
}
