package com.growplan.user.dto.response;

import com.growplan.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponse {

    @Schema(description = "사용자 아이디", example = "1")
    private final Long id;

    @Schema(description = "사용자 닉네임", example = "김동동")
    private final String nickname;

    @Schema(description = "사용자 이메일", example = "example@example.com")
    private final String email;

    @Schema(description = "사용자 소셜 로그인 타입 (kakao, naver)", example = "kakao")
    private final String socialLoginType;

    @Schema(description = "사용자 유효성 상태 (1: 유효, 0: 비유효)", example = "1")
    private final Integer isValid;

    public static UserResponse of(final User user) {
        return new UserResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getSocialLoginType().getCode(),
                user.getIsValid().getStatusCode()
        );
    }
}
