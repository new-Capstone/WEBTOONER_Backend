package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.dto.Message.MessageDto.MessageDeleteDto;
import com.capstone.ai_painter_backen.dto.Message.MessageDto.MessagePostDto;
import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import com.capstone.ai_painter_backen.service.Message.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    /*
    @GetMapping
    public ResponseEntity<?> getMessage(@RequestParam Long messageId){
        MessageDto.MessageResponseDto message = messageService.getMessage(messageId);
        return ResponseEntity.ok().body(message);
    }
    */

    @GetMapping
    @Operation(summary = "roomId로 해당 room에 속한 모든 메시지 조회")
    @ApiResponses({@ApiResponse(responseCode = "200" ,description = "message 정보가 정상적으로 나감",
            content = @Content(schema = @Schema(implementation = MessageDto.MessageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    public ResponseEntity<?> getMessages(@RequestParam Long roomId) {
        return ResponseEntity.ok().body(messageService.getMessages(roomId));
    }
}
