package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.dto.Message.MessageDto.MessageDeleteDto;
import com.capstone.ai_painter_backen.dto.Message.MessageDto.MessagePostDto;
import com.capstone.ai_painter_backen.dto.Result;
import com.capstone.ai_painter_backen.dto.image.AfterImageDto;
import com.capstone.ai_painter_backen.service.Message.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final MessageService messageService;

    //채팅
    @MessageMapping("chat/message")
    /**
     * TODO : chatPreHandler 구현하기
     * chatPreHandler -> jwt token 파싱하고 context에 user정보 저장
     */
    public ResponseEntity<?> message(MessageDto.MessagePostDto messagePostDto) {
        log.info("pub/chat/message");
        return ResponseEntity.ok().body(messageService.createMessage(messagePostDto));
    }

    @GetMapping("/{roomId}")
    @Operation(summary = "roomId로 해당 room에 속한 모든 메시지 조회")
    @ApiResponses({@ApiResponse(responseCode = "200" ,description = "message 정보가 정상적으로 나감",
            content = @Content(schema = @Schema(implementation = MessageDto.MessageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    public Result getMessages(@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(defaultValue = "10") Integer size,
                              @PathVariable Long roomId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<MessageDto.MessageResponseDto> messages = messageService.getMessages(pageable, roomId);
        return Result.createPage(messages);
    }

    @Operation(summary = "/pub/chat/message <- 이 url에 messagePostDto form으로 json 데이터 보내주면 OK")
    @ApiResponses({@ApiResponse(responseCode = "200" ,description = "message 정보가 정상적으로 나감",
            content = @Content(schema = @Schema(implementation = MessageDto.MessageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @GetMapping("/test1")
    public void test1(MessageDto.MessagePostDto messagePostDto) {
        log.info("Test 입니다.");
    }

    @Operation(summary = "/sub/chat/room/{id} <- 구독 url")
    @ApiResponses({@ApiResponse(responseCode = "200" ,description = "message 정보가 정상적으로 나감",
            content = @Content(schema = @Schema(implementation = MessageDto.MessageResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "서버에서 에러가 발생하였습니다.")})
    @GetMapping("/test2")
    public void test2(MessageDto.MessagePostDto messagePostDto) {
        log.info("Test 입니다.");
    }

}
