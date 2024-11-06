package com.growplan.survey.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildSurveyListRequest {

    @Valid
    @NotNull
    @Size(min = 1, message = "설문 항목이 최소 1개 이상 필요합니다.")
    private List<ChildSurveyRequest> surveys;
}
