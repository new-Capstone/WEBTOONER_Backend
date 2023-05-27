package com.capstone.ai_painter_backen.domain.image;

import com.capstone.ai_painter_backen.constant.Expression;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Embeddable
public class ImageContribute {

    private int angle;
    private String expression;
    /*
    String 으로 받아도 어지간하면 모델이 알아서 다 만들어줄듯?
    @Enumerated(EnumType.STRING)
    private Expression expression;
    */

    public ImageContribute(int angle, String expression) {
        this.angle = angle;
        this.expression = expression;
    }

    public ImageContribute() {
    }
}
