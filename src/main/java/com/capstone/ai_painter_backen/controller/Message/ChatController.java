package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.dto.Message.NotificationDto;
import com.capstone.ai_painter_backen.service.Message.MessageService;
import com.capstone.ai_painter_backen.service.Message.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;
    private final NotificationService notificationService;

    //pass
    //pub/chat/enter로 client가 요청하면 호출됨.
    @MessageMapping("chat/enter")
    public void enter(MessageDto.MessagePostDto messagePostDto) {
        messagePostDto.setContent(messagePostDto.getWriter() + "님이 채팅방에 참여하였습니다");
        log.info("pub/chat/enter");
        //sub/chat/room/{roomId}를 구독하고 있는 client에 message가 전달됨.
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + Long.toString(messagePostDto.getRoomEntityId()), messagePostDto);
    }

    //pass
    @MessageMapping("chat/message")
    public void message(MessageDto.MessagePostDto messagePostDto) {
        log.info("pub/chat/message");
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + Long.toString(messagePostDto.getRoomEntityId()), messagePostDto);
        MessageDto.MessageResponseDto messageResponseDto = messageService.createMessage(messagePostDto);

        NotificationDto.NotificationPostDto notificationPostDto = NotificationDto.NotificationPostDto.builder()
                .chatUserEntityId(messagePostDto.getChatUserEntityId())
                .roodEntityId(messagePostDto.getRoomEntityId())
                .content(messagePostDto.getContent())
                .messageEntityId(messageResponseDto.getMessageId()).build();
        notificationService.createNotification(notificationPostDto);
    }
}
