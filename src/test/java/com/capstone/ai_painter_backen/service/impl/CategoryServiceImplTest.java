package com.capstone.ai_painter_backen.service.impl;

import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.repository.mentor.CategoryRepository;
import com.capstone.ai_painter_backen.service.mentor.CategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.capstone.ai_painter_backen.dto.mentor.CategoryDto.*;

@Transactional
@SpringBootTest
class CategoryServiceImplTest {

    @Autowired CategoryService categoryService;
    @Autowired CategoryRepository categoryRepository;

    @Test
    void save() {
        RequestSaveDto requestSaveDto = RequestSaveDto.builder()
                .categoryName("jin")
                .build();

        Long savedId = categoryService.saveCategory(requestSaveDto);
        Optional<CategoryEntity> findCategory = categoryRepository.findById(savedId);

        Assertions.assertThat(findCategory.get().getCategoryName()).isEqualTo("jin");
    }



}