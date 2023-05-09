package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.dto.Message.MessageDto.MessageDeleteDto;
import com.capstone.ai_painter_backen.dto.Message.MessageDto.MessagePostDto;
import com.capstone.ai_painter_backen.service.Message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    //사용X
    @PostMapping("/new")
    public ResponseEntity<?> createMessage(@RequestBody MessagePostDto postDto){
        return ResponseEntity.ok().body(messageService.createMessage(postDto));
    }

    //사용X
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteMessage(MessageDeleteDto deleteDto) {
        messageService.deleteMessage(deleteDto);
        return ResponseEntity.ok().body("deleted MessageId:" + deleteDto.getMessageId());
    }

    //사용X
    @GetMapping
    public ResponseEntity<?> getMessage(@RequestParam Long messageId){
        MessageDto.MessageResponseDto message = messageService.getMessage(messageId);
        return ResponseEntity.ok().body(message);
    }
}
