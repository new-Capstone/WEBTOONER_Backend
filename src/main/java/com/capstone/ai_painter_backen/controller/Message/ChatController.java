package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.MessageDto;
import com.capstone.ai_painter_backen.dto.RoomDto;
import com.capstone.ai_painter_backen.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/")
    public ResponseEntity<?> test(){
        return ResponseEntity.ok().body("test");
    }
    @PostMapping("/createroom")
    public ResponseEntity<?> createRoom(@RequestBody RoomDto.PostDto postDto){
        return ResponseEntity.ok().body(chatService.createRoom(postDto));
    }

    @PostMapping("/sendchat")
    public ResponseEntity<?> sendChat(@RequestBody MessageDto.PostDto postDto){
        return ResponseEntity.ok().body(chatService.sendChat(postDto));
    }

    @DeleteMapping("deletechat")
    public ResponseEntity<?> deleteChat(MessageDto.DeleteDto deleteDto){
        chatService.deleteChat(deleteDto);
        return ResponseEntity.ok().body("deleted MessageId:"+deleteDto.getMessageId());
    }
    @DeleteMapping("deleteroom")
    public ResponseEntity<?> deleteRoom(RoomDto.DeleteDto deleteDto){
        chatService.deleteRoom(deleteDto);
        return ResponseEntity.ok().body("deleted RoomId:"+deleteDto.getRoomid());
    }

    @GetMapping("getRoom")
    public ResponseEntity<?> getRoom(@RequestParam Long roomId){
        return ResponseEntity.ok().body(chatService.getRoom(roomId));
    }
}
