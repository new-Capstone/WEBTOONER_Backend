package com.capstone.ai_painter_backen.service.impl;

import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.exception.DuplicateNameException;
import com.capstone.ai_painter_backen.mapper.CategoryMapper;
import com.capstone.ai_painter_backen.repository.CategoryRepository;
import com.capstone.ai_painter_backen.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.capstone.ai_painter_backen.controller.dto.CategoryDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Long saveCategory(RequestSaveDto requestSaveDto) {
        CategoryEntity category = categoryMapper.requestSaveDtoToEntity(requestSaveDto);
        log.info("section1 : name = {}", category.getCategoryName());

        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new DuplicateNameException();
        }

        return categoryRepository.save(category).getId();
    }

    @Override
    @Transactional
    public void updateCategory(Long id, RequestUpdateDto requestUpdateDto) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());

        category.update(requestUpdateDto);
    }

    @Override
    @Transactional
    public void deleteCategory(RequestDeleteDto deleteDto) {
        categoryRepository.deleteById(deleteDto.getCategoryId());
    }

    @Override
    public ResponseDto findCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());

        return categoryMapper.entityToResponseDto(category);
    }

    @Override
    public List<ResponseDto> findAllCategory() {
        return categoryRepository.findAll().stream()
                .map(m -> new ResponseDto(m.getId(), m.getCategoryName()))
                .collect(Collectors.toList());
    }
}
