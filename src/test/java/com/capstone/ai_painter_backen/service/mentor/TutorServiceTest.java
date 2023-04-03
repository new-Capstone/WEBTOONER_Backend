package com.capstone.ai_painter_backen.service.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.repository.mentor.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
class TutorServiceTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TutorService tutorService;

    @Transactional @Test
    public void categoryTutorEntityTest() throws Exception{
        //given
        String g = "느와르";
        CategoryEntity categoryEntity = new CategoryEntity("느와르");
        //when

        Assertions.assertThat(categoryRepository.save(categoryEntity).getCategoryName()).isEqualTo(g);

        //then
    }

    @Test @Transactional
    public void categoryTutorUpdateTest() throws Exception{
        //given


        //when

        //then

    }
}