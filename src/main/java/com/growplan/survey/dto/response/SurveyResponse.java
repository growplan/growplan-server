package com.growplan.survey.dto.response;

import com.growplan.survey.domain.ChildSurveyElement;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class SurveyResponse {

    private final List<SurveyDetailResponse> details;

    public static SurveyResponse of(final List<ChildSurveyElement> elements) {
        final List<SurveyDetailResponse> detailResponses = elements.stream()
                .map(element -> new SurveyDetailResponse(element.getId(), element.getScript()))
                .toList();

        return new SurveyResponse(developmentType, detailResponses);
    }
}