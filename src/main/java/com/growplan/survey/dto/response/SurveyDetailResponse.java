package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import com.growplan.survey.domain.Survey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SurveyDetailResponse {

    private final Long id;
    private final String script;
    private final Integer status;

    public static SurveyDetailResponse of(final Survey survey) {
        return new SurveyDetailResponse(
                survey.getId(),
                survey.getScript(),
                null
        );
    }

    public static SurveyDetailResponse of(final ChildSurvey childSurvey) {
        return new SurveyDetailResponse(
                childSurvey.getId(),
                childSurvey.getSurvey().getScript(),
                childSurvey.getStatus()
        );
    }
}