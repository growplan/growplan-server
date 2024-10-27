package com.growplan.login.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginStatusResponse {

    private final Boolean isLoggedIn;
    private final String accessToken;

    public static LoginStatusResponse of(
            final Boolean isLoggedIn,
            final String accessToken
    ) {
        return new LoginStatusResponse(isLoggedIn, accessToken);
    }
}
