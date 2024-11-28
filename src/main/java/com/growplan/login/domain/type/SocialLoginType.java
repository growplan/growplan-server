package com.growplan.login.domain.type;

import lombok.Getter;

@Getter
public enum SocialLoginType {

    KAKAO("kakao"),
    NAVER("naver");

    private final String code;

    SocialLoginType(final String code) {
        this.code = code;
    }
}
