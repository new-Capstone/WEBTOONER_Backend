package com.capstone.ai_painter_backen.dto.mentor;

import com.capstone.ai_painter_backen.domain.mentor.CategoryTutorEntity;
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
     public static class RequestSaveDto {
         private String categoryName;
     }

     @Getter
     @NoArgsConstructor
     @Builder
     @AllArgsConstructor
     public static class RequestUpdateDto {
          private String categoryName;
          //TODO :: Tutor 합칠 때 변경
     }

     @Getter
     @NoArgsConstructor
     @Builder
     @AllArgsConstructor
     public static class RequestDeleteDto {
          private Long categoryId;
     }

     @Getter
     @NoArgsConstructor
     @Builder
     @AllArgsConstructor
     public static class ResponseDto {
         private Long id;
         private String categoryName;
         private List<CategoryTutorEntity> categoryTutorEntities;
     }
}


