package com.capstone.ai_painter_backen.domain.image;

import com.capstone.ai_painter_backen.constant.Expression;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ImageContribute {

    private int angle;

    @Enumerated(EnumType.STRING)
    private Expression expression;
}
