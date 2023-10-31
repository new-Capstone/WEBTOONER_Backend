package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.message.RoomEntity;

import java.util.Optional;

public interface RoomRepositoryCustom {
    Optional<RoomEntity> findByIdWithUsers(Long roomId);
}
