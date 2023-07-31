package com.capstone.ai_painter_backen.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result<T> {
    private T data;
    private Integer size;
    private Integer number;
    private Integer total;
    private String error;
    private Boolean success;

    public static <E> Result<List<E>> createPage(Page<E> t) {
        return Result.<List<E>>builder()
                .data(t.getContent())
                .success(true)
                .total(t.getTotalPages())
                .number(t.getNumber())
                .size(t.getSize())
                .build();
    }
}
