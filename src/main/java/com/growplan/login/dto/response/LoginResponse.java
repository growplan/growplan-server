package com.growplan.login.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponse {

    @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1...")
    private final String accessToken;

    @Schema(description = "리프레시 토큰", example = "dXNlcjEyMy5yZWZyZX==...")
    private final String refreshToken;

    public static LoginResponse of(
            final String accessToken,
            final String refreshToken
    ) {
        return new LoginResponse(accessToken, refreshToken);
    }
}
