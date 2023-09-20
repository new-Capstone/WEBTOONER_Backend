package com.capstone.ai_painter_backen.repository.message;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.message.RoomEntity;
import com.capstone.ai_painter_backen.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class RoomRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void findByIdWithUsers() {
        //given
        UserEntity owner = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        UserEntity visitor = UserEntity.createUserEssential(UUID.randomUUID().toString(), "asdf", UUID.randomUUID().toString());
        userRepository.save(owner);
        userRepository.save(visitor);

        RoomEntity roomEntity = RoomEntity.builder()
                .owner(owner)
                .visitor(visitor)
                .messageEntities(new ArrayList<>())
                .build();
        roomRepository.save(roomEntity);

        //when
        RoomEntity findRoomEntity = roomRepository.findByIdWithUsers(roomEntity.getId()).get();

        //then
        Assertions.assertThat(findRoomEntity.getId()).isEqualTo(roomEntity.getId());
        Assertions.assertThat(findRoomEntity.getOwner()).isEqualTo(owner);
        Assertions.assertThat(findRoomEntity.getVisitor()).isEqualTo(visitor);
    }
}