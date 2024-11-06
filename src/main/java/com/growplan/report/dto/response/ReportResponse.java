package com.growplan.report.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ReportResponse {

    private final String developmentType;
    private final Double score;

    public static ReportResponse of(Map.Entry<String, Double> record) {
        return new ReportResponse(record.getKey(), record.getValue());
    }
}
