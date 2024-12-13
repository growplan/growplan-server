package com.growplan.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateRequest {

    @NotBlank(message = "사용자 이름을 입력해주세요.")
    @Schema(description = "사용자 이름", example = "김보리")
    private String name;
}
