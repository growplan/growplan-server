package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import com.growplan.survey.domain.Survey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SurveyDetailListResponse {

    private final List<SurveyDetailResponse> surveys;

    public static SurveyDetailListResponse fromSurveys(final List<Survey> surveys) {
        final List<SurveyDetailResponse> surveyResponses = surveys.stream()
                .map(SurveyDetailResponse::of)
                .toList();

        return new SurveyDetailListResponse(surveyResponses);
    }

    public static SurveyDetailListResponse fromChildSurveys(final List<ChildSurvey> childSurveys) {
        final List<SurveyDetailResponse> surveyResponses = childSurveys.stream()
                .map(childSurvey -> SurveyDetailResponse.of(childSurvey.getSurvey())) // ChildSurvey에서 Survey를 가져오는 메서드 호출
                .toList();

        return new SurveyDetailListResponse(surveyResponses);
    }
}
