package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import com.growplan.survey.domain.Feedback;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class DevelopmentResultResponse {

    @Schema(description = "설문 날짜", example = "2024-11-25")
    private final LocalDate surveyDate;

    @Schema(description = "발달 점수", example = "25")
    private final Integer totalScore;

    @Schema(description = "피드백 내용 리스트", example = "[\"잘했어요!\", \"조금 더 노력하세요.\"]")
    private final List<String> contents;

    public static DevelopmentResultResponse of(final LocalDate currentDate, final List<ChildSurvey> surveys, final Integer totalScore, final List<Feedback> feedbacks) {
        final List<String> contents = feedbacks.stream()
                .map(feedback -> feedback.getContent())
                .toList();

        return new DevelopmentResultResponse(
                currentDate,
                totalScore,
                contents
        );
    }
}
