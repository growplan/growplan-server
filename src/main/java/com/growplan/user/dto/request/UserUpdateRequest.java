package com.growplan.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {

    @NotBlank(message = "사용자 닉네임을 입력해주세요.")
    @Schema(description = "사용자 닉네임", example = "김보리")
    private String name;

    @NotBlank(message = "사용자 생년월일(yyyy-MM-dd)을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Schema(description = "사용자 생년월일 (yyyy-MM-dd 형식)", example = "1990-01-01")
    private String birthdate;

    @NotBlank(message = "사용자 이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @Schema(description = "사용자 이메일 주소", example = "example@example.com")
    private String email;

    @NotBlank(message = "사용자 전화번호를 입력해주세요.")
    @Pattern(regexp = "^\\d{11}$", message = "전화번호는 11자리의 숫자만 입력 가능합니다.")
    @Schema(description = "사용자 전화번호 (11자리, 숫자만 입력)", example = "01012345678")
    private String number;
}
