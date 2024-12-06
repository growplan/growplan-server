package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class SurveyListResponse {

    private final List<SurveyResponse> surveys;

    public static SurveyListResponse of(final List<ChildSurvey> childSurveys) {
        final List<SurveyResponse> surveyResponses = childSurveys.stream()
                .map(childSurvey -> SurveyResponse.of(childSurvey))
                .collect(Collectors.toList());

        return new SurveyListResponse(surveyResponses);
    }
}