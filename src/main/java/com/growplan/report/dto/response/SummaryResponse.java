package com.growplan.report.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SummaryResponse {

    private final String summary;

    public static SummaryResponse of(final String summary) {
        return new SummaryResponse(summary);
    }
}
