package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.MessageDto.PostDto;
import com.capstone.ai_painter_backen.service.Message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat/enter")
    public void enter(PostDto messageDto) {
        //writer filed 추가하면 user 안거쳐도 됨
        //messageDto.setContent(messageDto.getWriter() + "님이 채팅방에 참여하였습니다");
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getRoomEntityId(), messageDto);
    }

    @MessageMapping("/chat/message")
    public void message(PostDto messageDto) {
        simpMessagingTemplate.convertAndSend("/sub/chat/room/" + messageDto.getRoomEntityId(), messageDto);
        messageService.createMessage(messageDto);
    }
}
