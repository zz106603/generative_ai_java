package com.spring.ai.common.exception.custom;

import com.spring.ai.common.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class CustomNotFoundException extends BaseException {
    public <T> CustomNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public <T> CustomNotFoundException(Throwable cause) {
        super(HttpStatus.NOT_FOUND, cause.getMessage());
    }
}
