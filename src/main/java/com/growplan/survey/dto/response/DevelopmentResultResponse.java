package com.growplan.survey.dto.response;

import com.growplan.survey.domain.Survey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DevelopmentResultResponse {

    private final String developmentType;
    private final Integer totalScore;
    private final List<String> scripts;

    // TODO 상태에 대한 설명을 어디서 가져올 것인지
    public static DevelopmentResultResponse of(final List<Survey> surveys, final Integer totalScore) {
        final List<String> scripts = surveys.stream()
                .map(survey -> survey.getScript())
                .toList();

        return new DevelopmentResultResponse(
                surveys.get(0).getDevelopmentType().getType(),
                totalScore,
                scripts
        );
    }
}
