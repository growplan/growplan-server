package com.growplan.report.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ReportResponse {

    @Schema(description = "발달 영역", example = "발달 영역 (GM, LM, CG, LG, SC, SH)")
    private final String developmentType;

    @Schema(description = "발달 점수", example = "85.5")
    private final Double score;

    public static ReportResponse of(Map.Entry<String, Double> record) {
        return new ReportResponse(record.getKey(), record.getValue());
    }
}
