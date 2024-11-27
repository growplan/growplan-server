package com.growplan.login.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginStatusResponse {

    @Schema(description = "로그인 여부", example = "true")
    private final Boolean isLoggedIn;

    @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1...")
    private final String accessToken;

    public static LoginStatusResponse of(
            final Boolean isLoggedIn,
            final String accessToken
    ) {
        return new LoginStatusResponse(isLoggedIn, accessToken);
    }
}