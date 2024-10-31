package com.growplan.survey.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DevelopmentResultListResponse {

    private final Integer totalScore;
    private final List<String> scripts;

    // TODO 상태에 대한 설명을 어디서 가져올 것인지
    public DevelopmentResultListResponse of(final Integer totalScore, final List<String> scripts) {
        return new DevelopmentResultListResponse(
                totalScore,
                scripts
        );
    }
}
