package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.mentor.CategoryDto.RequestSaveDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto;
import com.capstone.ai_painter_backen.service.mentor.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.capstone.ai_painter_backen.dto.mentor.CategoryDto.*;

@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
@Tag(name="카테고리")
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/new")
    public ResponseEntity createCategory(@RequestBody @Schema(implementation = RequestSaveDto.class) RequestSaveDto requestSaveDto) {
        return ResponseEntity.ok().body(categoryService.saveCategory(requestSaveDto));
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
    public ResponseDto updateCategory(@PathVariable Long id, @RequestBody RequestUpdateDto requestUpdateDto) {
        return categoryService.updateCategory(id, requestUpdateDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(new RequestDeleteDto(id));
    }
    //Delete -> Tutor
}
