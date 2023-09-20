package com.capstone.ai_painter_backen.service.Message;


import com.capstone.ai_painter_backen.constant.MessageType;
import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.repository.message.MessageRepository;
import com.capstone.ai_painter_backen.repository.message.RoomRepository;
import com.capstone.ai_painter_backen.repository.UserRepository;
import com.capstone.ai_painter_backen.service.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final RedisUtil redisUtil;
    private final SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final SimpleDateFormat sdFormatToString = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    private final int maximumQueueSize = 10; // 30
    private final int commitSize = 5; // 20
    private final int pageSize = 8; // 10


    @Transactional
    public void insertMessage(MessageDto.MessagePostDto messagePostDto) {
        //룸이 있는지 체크
        roomRepository.findById(messagePostDto.getRoomEntityId()).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));

        MessageDto.MessageCacheDto messageCacheDto = MessageDto.MessageCacheDto.builder()
                .content(messagePostDto.getContent())
                .chatUserId(messagePostDto.getChatUserEntityId())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();

        //최초 입장 시 입장 메시지 처리
        if (messagePostDto.getType() == MessageType.ENTER) {
            UserEntity chatUser = userRepository.findById(messagePostDto.getChatUserEntityId()).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
            messageCacheDto.setContent(chatUser.getUserRealName() + "님이 입장했습니다.");
        }

        if (!redisUtil.containsKey(messagePostDto.getRoomEntityId())) {
            Queue<MessageDto.MessageCacheDto> messageQueue = new LinkedList<>();
            messageQueue.add(messageCacheDto);

            redisUtil.put(messagePostDto.getRoomEntityId(), messageQueue);
            redisUtil.putDummy(messagePostDto.getRoomEntityId());
        } else {
            Queue<MessageDto.MessageCacheDto> messageQueue = redisUtil.get(messagePostDto.getRoomEntityId());
            messageQueue.add(messageCacheDto);

            if (messageQueue.size() > maximumQueueSize) {
                Queue<MessageDto.MessageCacheDto> queue = new LinkedList<>();
                for (int i = 0; i < commitSize; i++) {
                    queue.add(messageQueue.poll());
                }
                commitMessage(queue, messagePostDto.getRoomEntityId());
            }

            redisUtil.put(messagePostDto.getRoomEntityId(), messageQueue);
            redisUtil.putDummy(messagePostDto.getRoomEntityId());
        }

        log.info("CacheSize : {}", redisUtil.get(messagePostDto.getRoomEntityId()).size());

        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + messagePostDto.getRoomEntityId(), messagePostDto);
    }

    @Transactional
    public void commitMessage(Queue<MessageDto.MessageCacheDto> queue, Long roomId) {
        for (int i = 0; i < commitSize; i++) {
            MessageDto.MessageCacheDto messageCacheDto = queue.poll();
            UserEntity chatUser = userRepository.findById(messageCacheDto.getChatUserId()).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

            RoomEntity chatRoom = roomRepository.findById(roomId).orElseThrow(
                    () -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));

            try {
                MessageEntity chatMessage = MessageEntity.builder()
                        .chatUserEntity(chatUser)
                        .roomEntity(chatRoom)
                        .content(messageCacheDto.getContent())
                        .registeredTime(sdFormatToString.parse(messageCacheDto.getCreatedAt()))
                        .build();

                messageRepository.save(chatMessage);
            } catch (ParseException e) {
                log.info("parse error");
            }
        }
    }

    public List<MessageDto.MessageResponseDto> getMessages(Pageable pageable, Long roomId) {
        //cache hit 경우, user 같이 안가져오면 한번 더 조회해야 함.
        //fetchJoin 으로 한 번에 가져오도록 변경
        RoomEntity roomEntity = roomRepository.findByIdWithUsers(roomId).orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.ROOM_NOT_FOUND));

        List<MessageDto.MessageResponseDto> messageResponses = new ArrayList<>();

        // Cache miss
        if (!redisUtil.containsKey(roomId)) {
            List<MessageEntity> messageEntities = messageRepository.findByRoomIdReverse(roomId, pageable);
            //DB 에도 없는 경우 (아직 채팅을 한 번도 안친 경우) -> 빈 List 반환
            if (messageEntities.isEmpty()) return messageResponses;

            //Cache 에 저장될 값
            List<MessageDto.MessageCacheDto> messageCacheDtos = messageEntities.stream()
                    .map(m -> new MessageDto.MessageCacheDto(m.getContent(),
                            sdFormat.format(m.getRegisteredTime()).toString(),
                            m.getChatUserEntity().getId()))
                    .collect(Collectors.toList());

            //Response 로 보낼 값
            List<MessageDto.MessageResponseDto> messageResponseDtos = messageEntities.stream()
                    .map(m -> new MessageDto.MessageResponseDto(m.getContent(),
                            m.getChatUserEntity().getUserRealName(),
                            sdFormat.format(m.getRegisteredTime()).toString(),
                            m.getChatUserEntity().getProfileImage()))
                    .collect(Collectors.toList());


            //Cache 및 Response 값 세팅
            Queue<MessageDto.MessageCacheDto> queue = new LinkedList<>(messageCacheDtos);
            redisUtil.put(roomId, queue);
            messageResponses = messageResponseDtos;
        } else { // Cache hit
            //이미지, 이름 조회를 각 메시지마다 하지 않고 한 번만 조회하도록 최적화
            String ownerName = roomEntity.getOwner().getUserRealName();
            String ownerImage = roomEntity.getOwner().getProfileImage();
            String visitorName = roomEntity.getVisitor().getUserRealName();
            String visitorImage = roomEntity.getVisitor().getProfileImage();

            LinkedList<MessageDto.MessageCacheDto> messageCacheDtos = redisUtil.get(roomId);
            log.info("chache 에 있는 메시지 개수 : {}", messageCacheDtos.size());

            //시작 조회 위치
            int start = pageable.getPageNumber() * pageSize;

            //페이지 오버
            if (start > messageCacheDtos.size() - 1) {
                return messageResponses;
            } else {
                for (int i = start; i < start + pageSize; i++) {
                    //Cache 초과
                    if (i > messageCacheDtos.size() - 1) {
                        if (!messageResponses.isEmpty()) {
                            Collections.reverse(messageResponses);
                        }
                        //남은 pageSize 만큼 DB 에서 가져오기
                        PageRequest pageRequest = PageRequest.of(0, pageSize - (i - start));
                        log.info("pageSize - (i - start) : {}", pageSize - (i - start));
                        List<MessageEntity> messageEntities = messageRepository.findByRoomIdReverse(roomId, pageRequest);
                        for (MessageEntity messageEntity : messageEntities) {
                            messageResponses.add(new MessageDto.MessageResponseDto(messageEntity.getContent(),
                                    messageEntity.getChatUserEntity().getUserRealName(),
                                    sdFormat.format(messageEntity.getRegisteredTime()),
                                    messageEntity.getChatUserEntity().getProfileImage()));
                        }

                        break;
                    }

                    MessageDto.MessageCacheDto messageCacheDto = messageCacheDtos.get(i);

                    MessageDto.MessageResponseDto messageResponseDto = null;
                    if (messageCacheDto.getChatUserId() == roomEntity.getOwner().getId()) {
                        messageResponseDto = getMessageResponse(ownerName, ownerImage, messageCacheDto);
                    } else {
                        messageResponseDto = getMessageResponse(visitorName, visitorImage, messageCacheDto);
                    }

                    log.info("Cache에 있는 값 사용");
                    messageResponses.add(messageResponseDto);
                }
            }
        }
        return messageResponses;
    }

    private MessageDto.MessageResponseDto getMessageResponse(String writer, String image, MessageDto.MessageCacheDto messageCacheDto) {
        return MessageDto.MessageResponseDto.builder()
                .content(messageCacheDto.getContent())
                .createdAt(messageCacheDto.getCreatedAt())
                .profileImage(image)
                .writer(writer)
                .build();
    }
}
