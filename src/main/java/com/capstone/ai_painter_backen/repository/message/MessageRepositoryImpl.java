package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.capstone.ai_painter_backen.domain.message.QMessageEntity.messageEntity;

@RequiredArgsConstructor
@Repository
public class MessageRepositoryImpl implements MessageRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<MessageEntity> findByRoomIdReverse(Long roomId, Pageable pageable) {
        return jpaQueryFactory
                .selectFrom(messageEntity)
                .innerJoin(messageEntity.chatUserEntity).fetchJoin()
                .where(messageEntity.roomEntity.id.eq(roomId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(messageEntity.id.desc())
                .fetch();
    }
}
