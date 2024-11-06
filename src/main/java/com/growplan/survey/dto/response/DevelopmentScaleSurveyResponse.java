package com.growplan.survey.dto.response;

import com.growplan.child.domain.UserChild;
import com.growplan.survey.domain.SurveyGroup;
import com.growplan.survey.domain.SurveyResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class DevelopmentScaleSurveyResponse {

    private final String name;
    private final Integer months;
    private final LocalDate surveyDate;
    private final List<DevelopmentScaleResponse> developments;
    private final List<SurveyTitleResponse> surveyTitles;

    public static DevelopmentScaleSurveyResponse of(
            final UserChild userChild,
            final Integer months,
            final List<SurveyResult> surveyResults,
            final List<SurveyGroup> surveyGroups
    ) {
        final LocalDate surveyDate = surveyResults.isEmpty() ? null : surveyResults.get(0).getUpdatedAt().toLocalDate();

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
