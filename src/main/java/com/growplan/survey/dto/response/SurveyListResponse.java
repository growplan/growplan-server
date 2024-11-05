package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class SurveyListResponse {

    private final List<SurveyResponse> surveys;

    public static SurveyListResponse of(final Map<String, List<ChildSurvey>> childSurveys) {
        final List<SurveyResponse> surveyResponses = childSurveys.entrySet().stream()
                .map(entry -> SurveyResponse.of(entry.getKey(), entry.getValue()))
                .toList();

        return new SurveyListResponse(surveyResponses);
    }
}
