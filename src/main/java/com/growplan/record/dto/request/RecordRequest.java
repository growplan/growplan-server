package com.growplan.record.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordRequest {

    @NotEmpty(message = "발달 영역을 하나 이상 선택해주세요.")
    @Schema(description = "발달 영역 리스트 (GM, LM, CG, LG, SC, SH 중 선택)",
            example = "[\"GM\", \"LM\"]",
            allowableValues = {"GM", "LM", "CG", "LG", "SC", "SH"})
    private List<@Pattern(regexp = "GM|LM|CG|LG|SC|SH", message = "유효한 발달 영역을 선택해주세요.") String> developmentTypes;

    @NotBlank(message = "발달 관련 설명을 입력해주세요.")
    @Schema(description = "발달 관련 설명", example = "아이의 언어 발달이 빠르게 이루어졌습니다.")
    private String script;
}
