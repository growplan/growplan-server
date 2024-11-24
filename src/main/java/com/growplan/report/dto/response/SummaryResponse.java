package com.growplan.report.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SummaryResponse {

    @Schema(description = "레포트 요약", example = "대근육 발달 수치가 16으로 낮습니다.")
    private final String summary;

    public static SummaryResponse of(final String summary) {
        return new SummaryResponse(summary);
    }
}
