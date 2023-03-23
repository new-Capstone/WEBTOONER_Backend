package com.capstone.ai_painter_backen.service.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.exception.DuplicateNameException;
import com.capstone.ai_painter_backen.mapper.CategoryMapper;
import com.capstone.ai_painter_backen.repository.mentor.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.capstone.ai_painter_backen.dto.mentor.CategoryDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Long saveCategory(RequestSaveDto requestSaveDto) {
        CategoryEntity category = categoryMapper.requestSaveDtoToEntity(requestSaveDto);

        if (categoryRepository.existsByCategoryName(category.getCategoryName())) {
            throw new DuplicateNameException();
        }

        return categoryRepository.save(category).getId();
    }


    @Transactional
    public ResponseDto updateCategory(Long id, RequestUpdateDto requestUpdateDto) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());
        category.update(requestUpdateDto.getCategoryName());

        return categoryMapper.entityToResponseDto(category);
    }


    @Transactional
    public void deleteCategory(RequestDeleteDto deleteDto) {
        categoryRepository.deleteById(deleteDto.getCategoryId());
    }


    public ResponseDto findCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException());

        return categoryMapper.entityToResponseDto(category);
    }


    public List<ResponseDto> findAllCategory() {
        return categoryRepository.findAll().stream()
                .map(m -> new ResponseDto(m.getId(), m.getCategoryName(), m.getCategoryTutorEntities()))
                .collect(Collectors.toList());
    }
}
