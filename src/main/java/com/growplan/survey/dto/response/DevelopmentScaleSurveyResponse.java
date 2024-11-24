package com.growplan.survey.dto.response;

import com.growplan.child.domain.UserChild;
import com.growplan.survey.domain.SurveyGroup;
import com.growplan.survey.domain.SurveyResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class DevelopmentScaleSurveyResponse {

    @Schema(description = "아이 이름", example = "홍길동")
    private final String name;

    @Schema(description = "아이 개월 수", example = "24")
    private final Integer months;

    @Schema(description = "설문 날짜 (YYYY-MM-DD 형식)", example = "2024-11-01")
    private final LocalDate surveyDate;

    private final List<DevelopmentScaleResponse> developments;
    private final List<SurveyTitleResponse> surveyTitles;

    public static DevelopmentScaleSurveyResponse of(
            final UserChild userChild,
            final Integer months,
            final List<SurveyResult> surveyResults,
            final List<SurveyGroup> surveyGroups
    ) {
        final LocalDate surveyDate = surveyResults.isEmpty() ? null : surveyResults.get(0).getCreatedAt().toLocalDate();

        final List<DevelopmentScaleResponse> scalesResponse = surveyResults.stream()
                .map(surveyResult -> DevelopmentScaleResponse.of(
                        surveyResult.getDevelopmentType().getType(),
                        surveyResult.getScore(),
                        surveyResult.getIsRisk()
                ))
                .toList();

        final List<SurveyTitleResponse> titleResponses = surveyGroups.stream()
                .map(surveyGroup -> SurveyTitleResponse.of(
                        surveyGroup.getDevelopmentType().getType(),
                        surveyGroup.getTitle()
                ))
                .toList();


        return new DevelopmentScaleSurveyResponse(
                userChild.getName(),
                months,
                surveyDate,
                scalesResponse,
                titleResponses
        );
    }
}
