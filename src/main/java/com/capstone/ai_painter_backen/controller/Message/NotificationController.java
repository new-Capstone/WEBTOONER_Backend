package com.capstone.ai_painter_backen.controller.Message;

import com.capstone.ai_painter_backen.dto.Message.NotificationDto;
import com.capstone.ai_painter_backen.service.Message.NotificationService;
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
    @ResponseStatus(HttpStatus.OK)
    @PostMapping()
    public List<NotificationDto.NotificationResponseDto> getNotifications(@RequestParam Long userId) {
        return notificationService.getNotificationsByUserId(userId);
    }

    //pass
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping()
    public void deleteNotifications(@RequestParam Long userId) {
        notificationService.deleteNotificationByUserId(userId);
    }
}
