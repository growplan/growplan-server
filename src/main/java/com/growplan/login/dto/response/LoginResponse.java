package com.growplan.login.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponse {

    private final String accessToken;
    private final String refreshToken;

    public static LoginResponse of(
            final String accessToken,
            final String refreshToken
    ) {
        return new LoginResponse(accessToken, refreshToken);
    }
}
