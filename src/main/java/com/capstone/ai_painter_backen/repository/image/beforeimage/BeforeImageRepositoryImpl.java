package com.capstone.ai_painter_backen.repository.image.beforeimage;

import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.capstone.ai_painter_backen.domain.QUserEntity.userEntity;
import static com.capstone.ai_painter_backen.domain.image.QBeforeImageEntity.beforeImageEntity;

@Repository
@RequiredArgsConstructor
public class BeforeImageRepositoryImpl implements BeforeImageRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;

    public Page<BeforeImageEntity> findAllByUserEntityId(Long userId, Pageable pageable) {
        List<BeforeImageEntity> content = jpaQueryFactory
                .selectFrom(beforeImageEntity)
                .innerJoin(beforeImageEntity.userEntity, userEntity)
                .fetchJoin()
                .where(userEntity.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(beforeImageEntity.count())
                .from(beforeImageEntity)
                .where(beforeImageEntity.userEntity.id.eq(userId));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }
}
