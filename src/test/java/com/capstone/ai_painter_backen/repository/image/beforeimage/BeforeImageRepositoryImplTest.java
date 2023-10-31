package com.capstone.ai_painter_backen.repository.image.beforeimage;

import com.capstone.ai_painter_backen.domain.UserEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BeforeImageRepositoryImplTest {

    @Autowired
    private BeforeImageRepository beforeImageRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllByUserEntityId() {
        //given
        UserEntity userEntity = UserEntity.createUserEssential(UUID.randomUUID().toString(), "qwer", UUID.randomUUID().toString());
        userRepository.save(userEntity);

        for (int i = 0; i < 10; i++) {
            BeforeImageEntity beforeImageEntity = getBeforeImageEntity(userEntity);
            beforeImageRepository.save(beforeImageEntity);
        }

        //when
        PageRequest pageRequest = PageRequest.of(0, 7, Sort.by("id"));
        Page<BeforeImageEntity> result = beforeImageRepository.findAllByUserEntityId(userEntity.getId(), pageRequest);

        //then
        Assertions.assertThat(result.getSize()).isEqualTo(7);
    }

    private BeforeImageEntity getBeforeImageEntity(UserEntity userEntity) {
        return BeforeImageEntity.builder()
                .userEntity(userEntity).build();
    }
}