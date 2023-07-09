package com.capstone.ai_painter_backen.controller.mentor;

import com.capstone.ai_painter_backen.dto.Result;
import com.capstone.ai_painter_backen.dto.mentor.CategoryDto;
import com.capstone.ai_painter_backen.dto.mentor.CategoryDto.RequestSaveDto;
import com.capstone.ai_painter_backen.dto.mentor.TuteeDto;
import com.capstone.ai_painter_backen.service.mentor.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    @Operation(summary = "Category 생성 api", description = "카테고리 이름 받아서 생성")
    @ApiResponse(responseCode = "201", description = "Crete Category")
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/new")
    public ResponseEntity createCategory(@Valid @RequestBody @Schema(implementation = RequestSaveDto.class) RequestSaveDto requestSaveDto) {
        return ResponseEntity.ok().body(categoryService.saveCategory(requestSaveDto));
    }

    @Operation(summary = "Category 모두 조회 api", description = "모든 카테고리 조회")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = CategoryDto.ResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Result getCategoryInfos() {
        return new Result(categoryService.findAllCategory());
    }

    @Operation(summary = "Category 조회 api", description = "categoryId로 조회")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = CategoryDto.ResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "404", description = "Not Found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseDto getCategoryInfo(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @Operation(summary = "Category 수정 api", description = "CategoryId, categoryName 받아서 수정")
    @ApiResponse(responseCode = "200", description = "OK",
            content = @Content(schema = @Schema(implementation = CategoryDto.ResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/edit")
    public ResponseDto updateCategory(@PathVariable Long id, @Valid @RequestBody RequestUpdateDto requestUpdateDto) {
        return categoryService.updateCategory(id, requestUpdateDto);
    }

    @Operation(summary = "Category 삭제 api", description = "CategoryId 받아서 삭제")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Client Error")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}/delete")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(new RequestDeleteDto(id));
    }
    //Delete -> Tutor
}
