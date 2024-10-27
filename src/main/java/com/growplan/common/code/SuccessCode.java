package com.growplan.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    OK_SUCCESS(200, "요청에 성공하였습니다."),

    NO_CONTENT_SUCCESS(204, "요청에 성공하였습니다.");

    private final int code;
    private final String message;
}
