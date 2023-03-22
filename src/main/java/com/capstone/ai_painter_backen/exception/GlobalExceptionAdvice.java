package com.capstone.ai_painter_backen.exception;



import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {// 프로젝트 전역에서 발생하는 모든 예외 잡아 handler로 처리한다
    @ExceptionHandler //  발생한 특정 예외를 잡아서 하나의 메소드에서 공통 처리해줄 수 있게 해준다.
    @ResponseStatus(HttpStatus.BAD_REQUEST)/* 400 BAD_REQUEST : 잘못된 요청 */
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final ErrorResponse response = ErrorResponse.of(e.getBindingResult());

        return response;
    }

    @ExceptionHandler
        @ResponseStatus(HttpStatus.BAD_REQUEST)/* 400 BAD_REQUEST : 잘못된 요청 */
        public ErrorResponse handleConstraintViolationException(ConstraintViolationException e) {
            final ErrorResponse response = ErrorResponse.of(e.getConstraintViolations());

            return response;
        }

        @ExceptionHandler
        public ResponseEntity handleBusinessLogicException(BusinessLogicException e) {
            final ErrorResponse response = ErrorResponse.of(e.getExceptionCode());

            return new ResponseEntity<>(response, HttpStatus.valueOf(e.getExceptionCode().getHttpStatus().value())
                );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {

        final ErrorResponse response = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED);

        return response;
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ErrorResponse NoSuchElementException(NoSuchElementException e) {

        final ErrorResponse response =  ErrorResponse.of(ExceptionCode.NO_SUCH_ELEMENT.getHttpStatus().value(), ExceptionCode.NO_SUCH_ELEMENT.getMessage());

        return response;
    }


}
