package com.growplan.survey.dto.response;

import com.growplan.survey.domain.Survey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SurveyListResponse {

    private final List<SurveyResponse> surveys;

    public static final SurveyListResponse of(final List<Survey> surveys) {
        final List<SurveyResponse> surveyResponses = surveys.stream()
                .map(survey -> SurveyResponse.of(survey))
                .toList();

        return new SurveyListResponse(surveyResponses);
    }
}
