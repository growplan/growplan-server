package com.growplan.survey.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SurveyTitleResponse {

    private final String developmentType;
    private final String title;

    public static SurveyTitleResponse of(
            final String developmentType,
            final String title
    ) {
        return new SurveyTitleResponse(
                developmentType,
                title
        );
    }
}
