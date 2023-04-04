package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.dto.mentor.CategoryDto;
import com.capstone.ai_painter_backen.service.Message.RoomService;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/new")
    public ResponseEntity<?> createRoom(@RequestBody @Schema(implementation = RoomDto.RoomPostDto.class)RoomDto.RoomPostDto postDto){
        return ResponseEntity.ok().body(roomService.createRoom(postDto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRoom(@RequestBody @Schema(implementation = RoomDto.RoomDeleteDto.class) RoomDto.RoomDeleteDto deleteDto){
        roomService.deleteRoom(deleteDto);
        return ResponseEntity.ok().body("deleted RoomId:"+deleteDto.getRoomId());
    }

    @GetMapping
    public ResponseEntity<?> getRoom(@RequestParam Long roomId){
        return ResponseEntity.ok().body(roomService.getRoom(roomId));
    }
}
