package com.growplan.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionCode {

    INVALID_REQUEST(1000, "올바르지 않은 요청입니다."),

    INVALID_REFRESH_TOKEN(2001, "유효하지 않은 RefreshToken입니다."),
    INVALID_ACCESS_TOKEN(2002, "유효하지 않은 AccessToken입니다."),
    EXPIRED_REFRESH_TOKEN(2003, "만료된 AccessToken입니다."),
    EXPIRED_ACCESS_TOKEN(2004, "만료된 RefreshToken입니다."),
    INVALIDATE_TOKEN(2005, "유효하지 않은 토큰입니다."),
    NOT_FOUND_USERNAME(2006, "존재하지 않는 아이디입니다."),
    INCORRECT_PASSWORD(2007, "비밀번호가 일치하지 않습니다."),
    NOT_FOUND_USER(2008, "존재하지 않는 회원입니다."),

    INVALID_GENDER_TYPE(3000, "유효하지 않은 성별 타입니다."),
    NOT_FOUND_USER_CHILD(3001, "요청에 해당하는 아이를 찾을 수 없습니다."),

    NOT_FOUND_CHILD_SURVEY(4001, "요청에 해당하는 설문을 찾을 수 없습니다."),

    NOT_FOUND_RECORD(4001, "요청에 해당하는 기록을 찾을 수 없습니다."),

    INTERNAL_SERVER_ERROR(9999, "서버에서 에러가 발생하였습니다.");

    private final int code;
    private final String message;
}
