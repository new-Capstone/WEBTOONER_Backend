package com.capstone.ai_painter_backen.repository;


import com.capstone.ai_painter_backen.domain.UserEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.capstone.ai_painter_backen.domain.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<UserEntity> findByEmailTest(String email) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(userEntity)
                .where(userEntity.userEmail.eq(email))
                .fetchOne());
    }
}
