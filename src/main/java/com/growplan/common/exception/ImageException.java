package com.growplan.common.exception;

import com.growplan.common.code.ExceptionCode;
import lombok.Getter;

@Getter
public class ImageException extends RuntimeException {

    private final int code;
    private final String message;

    public ImageException(final ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }
}
