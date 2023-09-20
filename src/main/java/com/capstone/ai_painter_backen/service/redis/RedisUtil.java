package com.capstone.ai_painter_backen.service.redis;

import com.capstone.ai_painter_backen.dto.Message.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, LinkedList<MessageDto.MessageCacheDto>> redisTemplate;

    @Value("${spring.redis.expire}")
    private int expireTime;

    public void put(Long roomId, Queue<MessageDto.MessageCacheDto> messageQueue) {
        redisTemplate.opsForValue().set(roomId.toString(), new LinkedList<>(messageQueue));
    }

    public void putDummy(Long roomId) {
        redisTemplate.opsForValue().set("room" + roomId.toString(), new LinkedList<>());
        redisTemplate.expire("room" + roomId.toString(), expireTime, TimeUnit.MINUTES);
    }

    public boolean containsKey(Long roomId) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(roomId.toString()));
    }

    public LinkedList<MessageDto.MessageCacheDto> get(Long roomId) {
        return redisTemplate.opsForValue().get(roomId.toString());
    }

    public Queue<MessageDto.MessageCacheDto> value() {
        return get(Long.valueOf(Objects.requireNonNull(redisTemplate.randomKey())));
    }

    public void deleteKey(Long roomId) {
        redisTemplate.delete(roomId.toString());
    }

    public void deleteAll() {
        redisTemplate.discard();
    }
}
