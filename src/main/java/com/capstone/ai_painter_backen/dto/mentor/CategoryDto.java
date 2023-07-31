package com.capstone.ai_painter_backen.dto.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CategoryDto {

     @Getter
     @NoArgsConstructor
     @Builder
     @AllArgsConstructor
     @Schema

     public static class RequestSaveDto {
          @NotBlank(message = "카테고리 이름은 필수입니다.")
          String categoryName;
     }

     @Getter
     @NoArgsConstructor
     @Builder
     @AllArgsConstructor
     @Schema

     public static class RequestUpdateDto {
          @NotBlank(message = "카테고리 이름은 필수입니다.")
          String categoryName;
     }

     @Getter
     @NoArgsConstructor
     @Builder
     @AllArgsConstructor
     public static class RequestDeleteDto {
           Long categoryId;
     }

     @Getter
     @NoArgsConstructor
     @Builder
     @AllArgsConstructor
     @Schema

     public static class ResponseDto {
          Long id;
          String categoryName;
          //List<CategoryTutorEntity> categoryTutorEntities;
     }
}


