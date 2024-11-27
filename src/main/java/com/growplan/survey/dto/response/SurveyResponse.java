package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SurveyResponse {

    @Schema(description = "설문 제목", example = "그려진 점선을 따라 그려요.")
    private final String title;

    private final List<SurveyDetailResponse> details;

    public static SurveyResponse of(final String title, final List<ChildSurvey> childSurveys) {
        final List<SurveyDetailResponse> detailResponses = childSurveys.stream()
                .map(SurveyDetailResponse::of)
                .toList();

        return new SurveyResponse(title, detailResponses);
    }
}