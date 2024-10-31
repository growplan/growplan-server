package com.growplan.survey.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class DevelopmentResultListResponse {

    private final List<DevelopmentResultResponse> developments;

    public static DevelopmentResultListResponse of(final Map<String, Integer> scoreMap) {
        final List<DevelopmentResultResponse> responses = scoreMap.entrySet().stream()
                .map(entry -> DevelopmentResultResponse.of(entry.getKey(), entry.getValue()))
                .toList();

        return new DevelopmentResultListResponse(responses);
    }
}
