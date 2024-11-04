package com.growplan.survey.dto.response;

import com.growplan.survey.domain.Survey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SurveyResponse {

    private final Long id;
    private final Double validAge;
    private final String script;
    private final String developmentType;

    public static SurveyResponse of(final Survey survey) {
        return new SurveyResponse(
                survey.getId(),
                survey.getValidAge(),
                survey.getScript(),
                survey.getDevelopmentType().getType()
        );
    }
}
