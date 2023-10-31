package com.capstone.ai_painter_backen.repository;

import com.capstone.ai_painter_backen.domain.UserEntity;

import java.util.Optional;

public interface UserRepositoryCustom {

    Optional<UserEntity> findByEmailTest(String email);
}
