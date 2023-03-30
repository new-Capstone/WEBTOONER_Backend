package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.service.Message.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/createroom")
    public ResponseEntity<?> createRoom(@RequestBody RoomDto.PostDto postDto){
        return ResponseEntity.ok().body(roomService.createRoom(postDto));
    }

    @DeleteMapping("deleteroom")
    public ResponseEntity<?> deleteRoom(RoomDto.DeleteDto deleteDto){
        roomService.deleteRoom(deleteDto);
        return ResponseEntity.ok().body("deleted RoomId:"+deleteDto.getRoomid());
    }

    @GetMapping("getRoom")
    public ResponseEntity<?> getRoom(@RequestParam Long roomId){
        return ResponseEntity.ok().body(roomService.getRoom(roomId));
    }
}
