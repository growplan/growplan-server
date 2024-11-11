package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import com.growplan.survey.domain.Feedback;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DevelopmentResultResponse {

    private final Integer totalScore;
    private final List<String> contents;

    public static DevelopmentResultResponse of(final List<ChildSurvey> surveys, final Integer totalScore, final List<Feedback> feedbacks) {
        final List<String> contents = feedbacks.stream()
                .map(feedback -> feedback.getContent())
                .toList();

        return new DevelopmentResultResponse(
                totalScore,
                contents
        );
    }
}
