package com.growplan.report.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ReportListResponse {

    private final Integer month;
    private final List<ReportResponse> reports;
    private final String summary;

    public static ReportListResponse of(final Integer month, final Map<String, Double> scoreMap, final String summary) {
        final List<ReportResponse> reportResponses = scoreMap.entrySet().stream()
                .map(entry -> new ReportResponse(entry.getKey(), entry.getValue()))
                .toList();

        return new ReportListResponse(month, reportResponses, summary);
    }
}
