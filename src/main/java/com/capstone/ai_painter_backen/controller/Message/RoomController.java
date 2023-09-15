package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.dto.mentor.CategoryDto;
import com.capstone.ai_painter_backen.service.Message.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;


    @Operation(summary = "Room 생성 api", description = "ownerId, visitorId로 Room 생성")
    @ApiResponse(responseCode = "201", description = "Create Room",
            content = @Content(schema = @Schema(implementation = RoomDto.RoomResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @PostMapping("/new")
    public ResponseEntity<?> createRoom(@Valid @RequestBody @Schema(implementation = RoomDto.RoomPostDto.class)RoomDto.RoomPostDto postDto){
        return ResponseEntity.ok().body(roomService.createRoom(postDto));
    }

    @Operation(summary = "Room 삭제 api", description = "roomId로 Room 삭제")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRoom(@Valid @RequestBody @Schema(implementation = RoomDto.RoomDeleteDto.class) RoomDto.RoomDeleteDto deleteDto){
        roomService.deleteRoom(deleteDto);
        return ResponseEntity.ok().body("deleted RoomId:"+deleteDto.getRoomId());
    }


    @Operation(summary = "RoomId로 Room 조회 api", description = "roomId로 Room 조회")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = RoomDto.RoomResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoom(@PathVariable Long roomId){
        return ResponseEntity.ok().body(roomService.getRoom(roomId));
    }

    @Operation(summary = "Room 조회 api", description = "userId로 Room 조회")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = RoomDto.RoomResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping
    public List<RoomDto.RoomResponseDto> getRooms() {
        return roomService.getRooms();
    }
}
