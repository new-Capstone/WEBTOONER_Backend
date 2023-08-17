package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<MessageEntity,Long> {

    @Override
    @Query("select m from MessageEntity m join fetch m.chatUserEntity join fetch m.roomEntity where m.roomEntity.id = :id")
    Optional<MessageEntity> findById(@Param("id") Long id);

    @Query("select m from MessageEntity m join fetch m.chatUserEntity join fetch m.roomEntity where m.roomEntity.id = :roomId")
    List<MessageEntity> findAllByRoomId(Long roomId);

    @Query(value = "select m from MessageEntity  m " +
            "join fetch m.chatUserEntity cu join fetch m.roomEntity r where r.id = :roomId",
            countQuery = "select count(m) from MessageEntity m join m.roomEntity r where r.id = :roomId")
    Page<MessageEntity> findAllByRoomIdWithRoomAndUser(Long roomId, Pageable pageable);
}
