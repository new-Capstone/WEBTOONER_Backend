package com.capstone.ai_painter_backen.repository.image.beforeimage;


import com.capstone.ai_painter_backen.domain.image.BeforeImageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BeforeImageRepositoryCustom {
    Page<BeforeImageEntity> findAllByUserEntityId(Long userId, Pageable pageable);
}
