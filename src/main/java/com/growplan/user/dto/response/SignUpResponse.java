package com.growplan.user.dto.response;

import com.growplan.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpResponse {

    @Schema(description = "사용자 아이디", example = "1")
    private final Long id;

    @Schema(description = "사용자 이름", example = "김동동")
    private final String name;

    @Schema(description = "사용자 생년월일", example = "1990-01-01")
    private final String birthdate;

    @Schema(description = "사용자 이메일", example = "example@example.com")
    private final String email;

    @Schema(description = "사용자 전화번호", example = "01012345678")
    private final String number;

    @Schema(description = "사용자 유효성 상태 (1: 유효, 0: 비유효)", example = "1")
    private final Integer isValid;

    public static SignUpResponse of(final User user) {
        return new SignUpResponse(
                user.getId(),
                user.getName(),
                user.getBirthdate(),
                user.getEmail(),
                user.getNumber(),
                user.getIsValid().getStatusCode()
        );
    }
}
