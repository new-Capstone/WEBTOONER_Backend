package com.capstone.ai_painter_backen.service.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryEntity;
import com.capstone.ai_painter_backen.exception.BusinessLogicException;
import com.capstone.ai_painter_backen.exception.DuplicateNameException;
import com.capstone.ai_painter_backen.exception.ExceptionCode;
import com.capstone.ai_painter_backen.mapper.etc.CategoryMapper;
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
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_CATEGORY_NAME);
        }

        return categoryRepository.save(category).getId();
    }


    @Transactional
    public ResponseDto updateCategory(Long id, RequestUpdateDto requestUpdateDto) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_CATEGORY));

        if (categoryRepository.existsByCategoryName(requestUpdateDto.getCategoryName())) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_CATEGORY_NAME);
        }

        category.update(requestUpdateDto.getCategoryName());

        return categoryMapper.entityToResponseDto(category);
    }

    /*
    카테고리 삭제하면 해당하는 Tutor는 어떻게 할까?
    1. 튜터도 같이 삭제
    2. default 카테고리를 하나 만들고 거기다가 집어넣기
    화요일에 회의 때 물어보기
     */
    @Transactional
    public void deleteCategory(RequestDeleteDto deleteDto) {
        if(categoryRepository.existsById(deleteDto.getCategoryId())) {
            categoryRepository.deleteById(deleteDto.getCategoryId());
        } else {
            throw new BusinessLogicException(ExceptionCode.NO_SUCH_CATEGORY);
        }
    }


    public ResponseDto findCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_CATEGORY));

        return categoryMapper.entityToResponseDto(category);
    }

    /*
    categoryResponseDto에 보면 List<CategoryTutorEntity>가 있는데 이거 삭제하는 게 좋을 것 같음.
    일단 주석처리 해놨음.
     */
    public List<ResponseDto> findAllCategory() {
        return categoryRepository.findAll().stream()
                .map(m -> new ResponseDto(m.getId(), m.getCategoryName()))
                .collect(Collectors.toList());
    }
}
