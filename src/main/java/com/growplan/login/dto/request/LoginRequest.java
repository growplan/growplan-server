package com.growplan.login.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequest {

    @NotNull(message = "아이디를 입력해주세요.")
    @Schema(description = "사용자 아이디", example = "user123")
    private String username;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Schema(description = "사용자 비밀번호", example = "password123!")
    private String password;
}
