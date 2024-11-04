package com.growplan.survey.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildSurveyUpdateRequest {

    @NotBlank(message = "숙련도를 입력해주세요.")
    private Integer status;
}
