package com.growplan.login.domain;

import com.growplan.user.domain.User;
import lombok.Getter;

@Getter
public class MemberInfo {

    private final User user;
    private final Boolean isAdditionalSignup;

    public MemberInfo(
            final User user,
            final Boolean isAdditionalSignup
    ) {
        this.user = user;
        this.isAdditionalSignup = isAdditionalSignup;
    }
}
