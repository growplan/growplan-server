package com.growplan.child.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildRequest {

    @NotBlank(message = "아이 이름을 입력해주세요.")
    @Schema(description = "아이 이름", example = "김동동")
    private String name;

    @NotBlank(message = "아이 생년월일을 입력해주세요.")
    @Schema(description = "아이 생년월일 (YYYY-MM-DD 형식)", example = "2012-08-08")
    private String birthdate;

    @NotBlank(message = "아이 성별을 입력해주세요.")
    @Schema(description = "아이 성별 (예: 남자 또는 여자)", example = "여자")
    private String gender;

    @NotNull(message = "신생아 키를 입력해주세요.")
    @Schema(description = "신생아 키(cm)", example = "50.0")
    private Double bornHeight;

    @NotNull(message = "신생아 몸무게를 입력해주세요.")
    @Schema(description = "신생아 몸무게(kg)", example = "3.2")
    private Double bornWeight;

    @NotNull(message = "이른둥이 여부를 입력해주세요.")
    @Schema(description = "이른둥이 여부 (true: 이른둥이 O, false: 이른둥이 X)", example = "false")
    private Boolean isPremature;

    @Schema(description = "출생 당시 주수 (nullable 가능)", nullable = true, example = "40.0")
    @DecimalMax(value = "37.0", inclusive = false, message = "주수는 37주 미만이어야 합니다.")
    private Double birthWeeks;
}
