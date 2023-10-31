package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.service.Message.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;
    private final int pageSize = 10;

    @MessageMapping("chat/message")
    public void sendMessage(MessageDto.MessagePostDto messagePostDto) {
        messageService.insertMessage(messagePostDto);
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "roomId로 해당 room에 속한 모든 메시지 조회")
    @ApiResponses({@ApiResponse(responseCode = "200" ,description = "message 정보가 정상적으로 나감",
            content = @Content(schema = @Schema(implementation = MessageDto.MessageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    public List<MessageDto.MessageResponseDto> getMessages(@RequestParam(defaultValue = "0") Integer page,
                                                           @PathVariable Long roomId) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("id").descending());
        List<MessageDto.MessageResponseDto> messages = messageService.getMessages(pageable, roomId);
        return messages;
    }
}
