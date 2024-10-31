package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DevelopmentDetailResultResponse {

    private final String developmentType;
    private final Integer totalScore;
    private final List<String> scripts;

    public static DevelopmentDetailResultResponse of(final List<ChildSurvey> surveys, final Integer totalScore) {
        final List<String> scripts = surveys.stream()
                .map(survey -> survey.getSurvey().getScript())
                .toList();

        return new DevelopmentDetailResultResponse(
                surveys.get(0).getSurvey().getDevelopmentType().getType(),
                totalScore,
                scripts
        );
    }
}
