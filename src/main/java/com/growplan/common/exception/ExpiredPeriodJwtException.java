package com.growplan.common.exception;

import com.growplan.common.code.ExceptionCode;
import lombok.Getter;

@Getter
public class ExpiredPeriodJwtException extends AuthException {

    public ExpiredPeriodJwtException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}