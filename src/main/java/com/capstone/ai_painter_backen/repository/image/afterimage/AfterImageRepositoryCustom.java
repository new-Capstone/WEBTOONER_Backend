package com.capstone.ai_painter_backen.repository.image.afterimage;

import com.capstone.ai_painter_backen.domain.image.AfterImageEntity;

import java.util.List;

public interface AfterImageRepositoryCustom {
    List<AfterImageEntity> findAllByBeforeImageEntityId(Long beforeEntityId);
}
