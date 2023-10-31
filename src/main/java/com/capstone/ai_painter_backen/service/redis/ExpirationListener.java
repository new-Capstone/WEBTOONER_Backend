package com.capstone.ai_painter_backen.service.redis;

import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import com.capstone.ai_painter_backen.service.Message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@RequiredArgsConstructor
@Component
@Slf4j
public class ExpirationListener implements MessageListener {

    private final RedisUtil redisUtil;
    private final MessageService messageService;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        log.info("onMessage Key expiration {}", message.toString());
        Long roomId = Long.valueOf(message.toString().substring(4));
        LinkedList<MessageDto.MessageCacheDto> messageCacheDtos = redisUtil.get(roomId);
        Queue<MessageDto.MessageCacheDto> messageQueue = new LinkedList<>(messageCacheDtos);

        messageService.commitMessage(messageQueue, roomId);

        redisUtil.deleteKey(roomId);
    }
}
