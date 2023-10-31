package com.capstone.ai_painter_backen.repository;

import com.capstone.ai_painter_backen.domain.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void querydslTest() {
        //given
        String email = UUID.randomUUID().toString();
        String name = "qwer";
        String password = UUID.randomUUID().toString();

        UserEntity userEntity = UserEntity.createUserEssential(email, name, password);
        userRepository.save(userEntity);

        //when
        UserEntity findUserEntity = userRepository.findByEmailTest(email).get();

        //then
        Assertions.assertThat(userEntity).isEqualTo(findUserEntity);
    }
}