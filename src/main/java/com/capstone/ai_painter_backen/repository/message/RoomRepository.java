package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findAllByOwnerOrVisitor(UserEntity owner, UserEntity visitor);

    @Query("select r from RoomEntity r join UserEntity u where r.visitor.id = :userId or r.owner.id = :userId")
    Optional<RoomEntity> findRoomByUserId(Long userId);


    //notification 삭제 용도, 페이징 X
    @Query("select r from RoomEntity r join fetch r.messageEntities ms")
    Optional<RoomEntity> findRoomByIdWithMessage(Long roomId);
}
