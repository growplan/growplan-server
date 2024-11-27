package com.growplan.report.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ReportListResponse {

    @Schema(description = "발달 레포트 제공 월", example = "8")
    private final Integer month;

    @Schema(description = "발달 레포트 목록")
    private final List<ReportResponse> reports;

    public static ReportListResponse of(final Integer month, final Map<String, Double> scoreMap) {
        final List<ReportResponse> reportResponses = scoreMap.entrySet().stream()
                .map(entry -> new ReportResponse(entry.getKey(), entry.getValue()))
                .toList();

        return new ReportListResponse(month, reportResponses);
    }
}
