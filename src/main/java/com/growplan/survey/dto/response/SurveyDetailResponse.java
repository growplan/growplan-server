package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurvey;
import com.growplan.survey.domain.Survey;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SurveyDetailResponse {

    @Schema(description = "설문 아이디", example = "1")
    private final Long surveyId;

    @Schema(description = "아이의 설문 아이디 (설문이 완료되지 않았을 경우 null)", example = "1")
    private final Long childSurveyId;

    @Schema(description = "설문 내용", example = "아이가 2~3단어로 문장을 만드나요?")
    private final String script;

    @Schema(description = "설문 점수 (0~3)", example = "1")
    private final Integer status;

    public static SurveyDetailResponse of(final Survey survey) {
        return new SurveyDetailResponse(
                survey.getId(),
                null,
                survey.getScript(),
                null
        );
    }

    public static SurveyDetailResponse of(final ChildSurvey childSurvey) {
        return new SurveyDetailResponse(
                childSurvey.getSurvey().getId(),
                childSurvey.getId(),
                childSurvey.getSurvey().getScript(),
                childSurvey.getStatus()
        );
    }
}