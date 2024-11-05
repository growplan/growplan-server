package com.growplan.survey.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DevelopmentScaleResponse {

    private final String developmentType;
    private final Integer score;
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
