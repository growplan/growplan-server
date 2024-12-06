package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class SurveyResponse {

    @Schema(description = "설문 아이디", example = "1")
    private final Long surveyId;

    @Schema(description = "아이의 설문 아이디 (설문이 완료되지 않았을 경우 null)", example = "1")
    private final Long childSurveyId;

    @Schema(description = "설문 내용", example = "아이가 2~3단어로 문장을 만드나요?")
    private final String script;

    @Schema(description = "설문 점수 (0~3)", example = "1")
    private final Integer status;

    @Schema(description = "설문 날짜 (YYYY-MM-DD 형식)", example = "2024-11-01")
    private final LocalDate surveyDate;

    @Schema(description = "발달 영역", example = "GM", allowableValues = {"GM", "LM", "CG", "LG", "SC", "SH"})
    private final String developmentType;

    public static SurveyResponse of(final ChildSurvey childSurvey) {
        return new SurveyResponse(
                childSurvey.getSurvey().getId(),
                childSurvey.getId(),
                childSurvey.getSurvey().getScript(),
                childSurvey.getStatus(),
                childSurvey.getCreatedAt().toLocalDate(),
                childSurvey.getSurvey().getSurveyGroup().getDevelopmentType().getType()
        );
    }
}