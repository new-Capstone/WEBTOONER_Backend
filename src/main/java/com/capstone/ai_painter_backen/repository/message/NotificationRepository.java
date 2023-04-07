package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findALlByUserAndChecked(UserEntity userEntity, boolean checked);
    NotificationEntity findByMessage(MessageEntity messageEntity);
}
