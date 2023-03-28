package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.service.Message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/createmessage")
    public ResponseEntity<?> createMessage(@RequestBody MessageDto.PostDto postDto){
        return ResponseEntity.ok().body(messageService.createMessage(postDto));
    }

    @DeleteMapping("deletemessage")
    public ResponseEntity<?> deleteMessage(MessageDto.DeleteDto deleteDto) {
        messageService.deleteMessage(deleteDto);
        return ResponseEntity.ok().body("deleted MessageId:" + deleteDto.getMessageId());
    }

    @GetMapping("getmessage")
    public ResponseEntity<?> getMessage(@RequestParam Long messageId){
        MessageDto.ResponseDto message = messageService.getMessage(messageId);
        return ResponseEntity.ok().body(message);
    }
}
