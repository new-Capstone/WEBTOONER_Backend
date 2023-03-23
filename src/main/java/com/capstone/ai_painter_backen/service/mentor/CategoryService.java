package com.capstone.ai_painter_backen.service.mentor;

import java.util.List;

import static com.capstone.ai_painter_backen.dto.mentor.CategoryDto.*;

public interface CategoryService {
    Long saveCategory(RequestSaveDto requestSaveDto);
    void updateCategory(Long id, RequestUpdateDto requestUpdateDto);
    void deleteCategory(RequestDeleteDto deleteDto);
    List<ResponseDto> findAllCategory();
    ResponseDto findCategoryById(Long id);
}
