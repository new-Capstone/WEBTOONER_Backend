package com.capstone.ai_painter_backen.service;

import com.capstone.ai_painter_backen.controller.dto.CategoryDto;

import java.util.List;

import static com.capstone.ai_painter_backen.controller.dto.CategoryDto.*;

public interface CategoryService {
    Long saveCategory(RequestSaveDto requestSaveDto);
    void updateCategory(Long id, RequestUpdateDto requestUpdateDto);
    void deleteCategory(RequestDeleteDto deleteDto);
    List<ResponseDto> findAllCategory();
    ResponseDto findCategoryById(Long id);
}
