package com.growplan.survey.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DevelopmentScaleResponse {

    @Schema(description = "발달 영역 유형", example = "GM", allowableValues = {"GM", "LM", "CG", "LG", "SC", "SH"})
    private final String developmentType;

    @Schema(description = "발달 점수", example = "85")
    private final Integer score;

    @Schema(description = "위험 여부 (true: 위험, false: 정상)", example = "false")
    private final Boolean isRisk;

    public static DevelopmentScaleResponse of(
            final String developmentType,
            final Integer score,
            final Boolean isRisk
    ) {
        return new DevelopmentScaleResponse(
                developmentType,
                score,
                isRisk
        );
    }
}
