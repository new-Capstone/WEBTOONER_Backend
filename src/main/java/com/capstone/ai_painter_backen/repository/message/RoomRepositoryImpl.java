package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import static com.capstone.ai_painter_backen.domain.message.QRoomEntity.roomEntity;

@RequiredArgsConstructor
@Repository
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<RoomEntity> findByIdWithUsers(Long roomId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(roomEntity)
                .innerJoin(roomEntity.owner).fetchJoin()
                .innerJoin(roomEntity.visitor).fetchJoin()
                .where(roomEntity.id.eq(roomId))
                .fetchOne());
    }
}
