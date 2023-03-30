package com.capstone.ai_painter_backen.repository;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
