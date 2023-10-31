package com.capstone.ai_painter_backen.repository.image.afterimage;

import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.capstone.ai_painter_backen.domain.image.QAfterImageEntity.afterImageEntity;
import static com.capstone.ai_painter_backen.domain.image.QBeforeImageEntity.beforeImageEntity;

@Repository
@RequiredArgsConstructor
public class AfterImageRepositoryImpl implements AfterImageRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<AfterImageEntity> findAllByBeforeImageEntityId(Long beforeEntityId) {
        return jpaQueryFactory
                .selectFrom(afterImageEntity)
                .innerJoin(afterImageEntity.beforeImageEntity, beforeImageEntity).fetchJoin()
                .where(beforeImageEntity.id.eq(beforeEntityId))
                .fetch();
    }
}
