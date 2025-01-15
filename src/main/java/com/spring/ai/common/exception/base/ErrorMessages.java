package com.spring.ai.common.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessages {
    NOT_FOUND("E404-001", "Not Found");

    private final String code;
    private final String message;
}
