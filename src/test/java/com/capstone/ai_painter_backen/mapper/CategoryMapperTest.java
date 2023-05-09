package com.capstone.ai_painter_backen.mapper;

import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.mapper.etc.CategoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.capstone.ai_painter_backen.dto.mentor.CategoryDto.*;
import static org.assertj.core.api.Assertions.*;


@Transactional
@SpringBootTest
class CategoryMapperTest {

    @Autowired
    CategoryMapper mapper;

    @Test
    void dtoToEntity() {
        RequestSaveDto requestSaveDto = RequestSaveDto.builder().categoryName("jin").build();
        CategoryEntity category = mapper.requestSaveDtoToEntity(requestSaveDto);

        assertThat(requestSaveDto.getCategoryName()).isEqualTo(category.getCategoryName());
    }

    @Test
    void entityToDto() {
        CategoryEntity category = CategoryEntity.builder()
                .id(1L)
                .categoryName("jin")
                .build();

        ResponseDto responseDto = mapper.entityToResponseDto(category);

        assertThat(responseDto.getCategoryName()).isEqualTo(category.getCategoryName());
        assertThat(responseDto.getId()).isEqualTo(category.getId());
    }
}