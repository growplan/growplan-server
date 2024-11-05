package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SurveyResponse {

    private final String title;
    private final List<SurveyDetailResponse> details;

    public static SurveyResponse of(final String title, final List<ChildSurvey> childSurveys) {
        final List<SurveyDetailResponse> detailResponses = childSurveys.stream()
                .map(SurveyDetailResponse::of)
                .toList();

        return new SurveyResponse(title, detailResponses);
    }
}