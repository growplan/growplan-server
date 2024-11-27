package com.growplan.survey.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SurveyTitleResponse {

    @Schema(description = "발달 영역", example = "발달 영역 (GM, LM, CG, LG, SC, SH)")
    private final String developmentType;

    @Schema(description = "제목", example = "설문 제목")
    private final String title;

    public static SurveyTitleResponse of(
            final String developmentType,
            final String title
    ) {
        return new SurveyTitleResponse(
                developmentType,
                title
        );
    }
}
