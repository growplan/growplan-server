package com.growplan.common.type;

import lombok.Getter;

@Getter
public enum StatusType {

    DELETED(0),
    ACTIVE(1);

    private final Integer statusCode;

    StatusType(final Integer statusCode) {
        this.statusCode = statusCode;
    }
}
