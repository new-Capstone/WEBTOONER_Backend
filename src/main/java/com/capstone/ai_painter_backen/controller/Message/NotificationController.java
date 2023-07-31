package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.NotificationDto;
import com.capstone.ai_painter_backen.dto.Message.RoomDto;
import com.capstone.ai_painter_backen.dto.Result;
import com.capstone.ai_painter_backen.service.Message.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("notification")
public class NotificationController {

    private final NotificationService notificationService;

    //pass
    //조회할 때 checked 값이 바뀜 -> PostMapping

    @Operation(summary = "Notification 조회 api", description = "userId로 해당 유저 알림 모두 조회")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = NotificationDto.NotificationResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public List<NotificationDto.NotificationResponseDto> getNotifications(@RequestParam Long userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    @Operation(summary = "Notification 삭제 api", description = "userId 해당 유저 모든 알림 제거")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    public void deleteNotifications(@RequestParam Long userId) {
        notificationService.deleteNotificationByUserId(userId);
    }
}
