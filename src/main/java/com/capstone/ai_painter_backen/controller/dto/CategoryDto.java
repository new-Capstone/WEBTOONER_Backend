package com.capstone.ai_painter_backen.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
         //TODO : Tutor list
     }
}


