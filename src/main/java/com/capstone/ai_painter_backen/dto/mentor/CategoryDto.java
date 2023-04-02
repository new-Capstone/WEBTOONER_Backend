package com.capstone.ai_painter_backen.dto.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
          String categoryName;
     }

     @Getter
     @NoArgsConstructor
     @Builder
     @AllArgsConstructor
     @Schema

     public static class RequestUpdateDto {
           String categoryName;
          //TODO :: Tutor 합칠 때 변경
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
          List<CategoryTutorEntity> categoryTutorEntities;
     }
}


