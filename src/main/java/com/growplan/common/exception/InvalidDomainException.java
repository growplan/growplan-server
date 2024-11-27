package com.growplan.common.exception;

import com.growplan.common.code.ExceptionCode;
import lombok.Getter;

@Getter
public class InvalidDomainException extends BadRequestException {

    public InvalidDomainException(final ExceptionCode exceptionCode) {
        super(exceptionCode);
    }
}