package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.MessageEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MessageRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void findByRoomIdReverse() {
        //given
        UserEntity owner = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        UserEntity visitor = UserEntity.createUserEssential(UUID.randomUUID().toString(), "asdf", UUID.randomUUID().toString());
        userRepository.save(owner);
        userRepository.save(visitor);

        RoomEntity roomEntity = getRoom(owner, visitor);
        roomRepository.save(roomEntity);

        for (int i = 0; i < 20; i++) {
            MessageEntity messageEntity = MessageEntity.builder()
                    .roomEntity(roomEntity)
                    .chatUserEntity(owner)
                    .content("hello " + i)
                    .build();
            messageRepository.save(messageEntity);
        }

        //when
        PageRequest pageRequest1 = PageRequest.of(0, 10);
        PageRequest pageRequest2 = PageRequest.of(1, 8);
        List<MessageEntity> findMessageEntities1 = messageRepository.findByRoomIdReverse(roomEntity.getId(), pageRequest1);
        List<MessageEntity> findMessageEntities2 = messageRepository.findByRoomIdReverse(roomEntity.getId(), pageRequest2);

        //then
        Assertions.assertThat(findMessageEntities1.size()).isEqualTo(10);
        Assertions.assertThat(findMessageEntities1.get(0).getId()).isEqualTo(20);

        Assertions.assertThat(findMessageEntities2.size()).isEqualTo(8);
        Assertions.assertThat(findMessageEntities2.get(0).getId()).isEqualTo(12);
    }

    private RoomEntity getRoom(UserEntity owner, UserEntity visitor) {
        return RoomEntity.builder()
                .owner(owner)
                .visitor(visitor)
                .build();
    }
}