package com.growplan.survey.dto.request;

import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildSurveyRequest {

    @NotNull(message = "설문 아이디를 입력해주세요.")
    private Long id;

    @Min(0)
    @Max(3)
    @NotNull(message = "설문 점수를 입력해주세요.")
    private Integer status;
}
