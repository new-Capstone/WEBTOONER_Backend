package com.capstone.ai_painter_backen.exception;

import lombok.Getter;

import java.io.Serializable;

//커스텀 Exception
public class BusinessLogicException extends RuntimeException implements Serializable {

    @Getter
    private ExceptionCode exceptionCode; // 커스텀 예외 코드번호

    public BusinessLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }
}
