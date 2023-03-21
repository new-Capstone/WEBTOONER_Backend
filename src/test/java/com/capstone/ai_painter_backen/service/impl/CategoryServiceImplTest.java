package com.capstone.ai_painter_backen.service.impl;

import com.capstone.ai_painter_backen.controller.dto.CategoryDto;
import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.repository.CategoryRepository;
import com.capstone.ai_painter_backen.service.CategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.capstone.ai_painter_backen.controller.dto.CategoryDto.*;
import static org.junit.jupiter.api.Assertions.*;

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