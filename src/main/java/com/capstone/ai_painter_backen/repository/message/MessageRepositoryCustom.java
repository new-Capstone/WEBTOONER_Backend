package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MessageRepositoryCustom {
    List<MessageEntity> findByRoomIdReverse(Long roomId, Pageable pageable);
}
