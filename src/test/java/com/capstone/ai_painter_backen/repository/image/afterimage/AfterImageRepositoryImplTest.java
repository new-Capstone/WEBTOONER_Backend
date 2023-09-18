package com.capstone.ai_painter_backen.repository.image.afterimage;

import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;
import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import com.capstone.ai_painter_backen.repository.image.beforeimage.BeforeImageRepository;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AfterImageRepositoryImplTest {

    @Autowired
    private AfterImageRepository afterImageRepository;

    @Autowired
    private BeforeImageRepository beforeImageRepository;


    @Test
    void findAllByBeforeImageEntityId() {
        //given
        BeforeImageEntity beforeImageEntity = BeforeImageEntity.builder().build();
        beforeImageRepository.save(beforeImageEntity);

        for (int i = 0; i < 4; i++) {
            AfterImageEntity afterImageEntity = AfterImageEntity.builder()
                    .beforeImageEntity(beforeImageEntity).build();
            afterImageRepository.save(afterImageEntity);
        }

        //when
        List<AfterImageEntity> result = afterImageRepository.findAllByBeforeImageEntityId(beforeImageEntity.getId());

        //then
        Assertions.assertThat(result.size()).isEqualTo(4);
    }
}