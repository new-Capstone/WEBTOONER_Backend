package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.message.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
