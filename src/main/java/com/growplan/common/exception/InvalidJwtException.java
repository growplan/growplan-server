package com.growplan.common.exception;

import com.growplan.common.code.ExceptionCode;
import lombok.Getter;

@Getter
public class InvalidJwtException extends AuthException {

    public InvalidJwtException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}