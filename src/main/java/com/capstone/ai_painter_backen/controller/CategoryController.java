package com.capstone.ai_painter_backen.controller;

import com.capstone.ai_painter_backen.controller.dto.CategoryDto.RequestSaveDto;
import com.capstone.ai_painter_backen.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.capstone.ai_painter_backen.controller.dto.CategoryDto.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/new")
    public void createCategory(@RequestBody RequestSaveDto requestSaveDto) {
        categoryService.saveCategory(requestSaveDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ResponseDto> getCategoryInfos() {
        return categoryService.findAllCategory();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseDto getCategoryInfo(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/edit")
    public void updateCategory(@PathVariable Long id, @RequestBody RequestUpdateDto requestUpdateDto) {
        categoryService.updateCategory(id, requestUpdateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(new RequestDeleteDto(id));
    }
    //Delete -> Tutor
}
