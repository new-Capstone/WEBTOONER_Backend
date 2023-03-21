package com.capstone.ai_painter_backen.repository;

import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
