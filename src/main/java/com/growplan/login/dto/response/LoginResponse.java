package com.growplan.login.dto.response;

import com.growplan.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginResponse {

    @Schema(description = "사용자 아이디", example = "1")
    private final Long id;

    @Schema(description = "사용자 이메일", example = "1")
    private final String email;

    @Schema(description = "사용자 소셜 로그인 타입", example = "")
    private final String socialLoginType;

    @Schema(description = "사용자 추가 회원가입 유무", example = "true")
    private final Boolean isAdditionalSignup;

    @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1...")
    private final String accessToken;

    @Schema(description = "리프레시 토큰", example = "dXNlcjEyMy5yZWZyZX==...")
    private final String refreshToken;

    public static LoginResponse of(
            final User user,
            final Boolean isAdditionalSignup,
            final String accessToken,
            final String refreshToken
    ) {
        return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getSocialLoginType().getCode(),
                isAdditionalSignup,
                accessToken,
                refreshToken
        );
    }
}
