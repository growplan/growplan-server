package com.growplan.survey.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DevelopmentResultResponse {

    private final String developmentType;
    private final Integer totalScore;

    public static DevelopmentResultResponse of(final String developmentType, final Integer totalScore) {
        return new DevelopmentResultResponse(
                developmentType,
                totalScore
        );
    }
}
