package com.growplan.login.domain;

import com.growplan.login.domain.type.SocialLoginType;
import lombok.Getter;

@Getter
public class OauthMember {

    private final String email;
    private final String nickname;
    private final String socialLoginId;
    private final SocialLoginType socialLoginType;

    public OauthMember(
            final String email,
            final String nickname,
            final String socialLoginId,
            final SocialLoginType socialLoginType
    ) {
        this.email = email;
        this.nickname = nickname;
        this.socialLoginId = socialLoginId;
        this.socialLoginType = socialLoginType;
    }
}
