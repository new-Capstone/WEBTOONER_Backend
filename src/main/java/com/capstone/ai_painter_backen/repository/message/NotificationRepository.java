package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    @Override
    @Query("select n from NotificationEntity n join fetch n.message join fetch n.user where n.id = :id")
    Optional<NotificationEntity> findById(@Param("id") Long id);

    @Query("select n from NotificationEntity n join fetch n.message join fetch n.user where n.user = :user and n.checked = :checked")
    List<NotificationEntity> findALlByUserAndChecked(@Param("user") UserEntity userEntity, @Param("checked") boolean checked);

    @Query("select n from NotificationEntity n join fetch n.message join fetch n.user where n.message = :message")
    Optional<NotificationEntity> findByMessage(@Param("message") MessageEntity messageEntity);
}
