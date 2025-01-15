package com.spring.ai.common.exception.handler;

import com.spring.ai.common.exception.base.BaseException;
import com.spring.ai.common.exception.base.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExcpetionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(BaseException e) {
        log.error("Error occurred: {}", e.getMessage());
        ErrorResponse response = new ErrorResponse(e.isSuccess(), e.getMessage());
        return new ResponseEntity<>(response, e.getStatus());
    }
}
