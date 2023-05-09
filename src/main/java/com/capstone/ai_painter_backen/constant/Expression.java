package com.capstone.ai_painter_backen.constant;

public enum Expression {
    HAPPY("행복"),

    SAD("슬픔"),

    ANGRY("분노");


    private String expression;

    Expression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }
}
