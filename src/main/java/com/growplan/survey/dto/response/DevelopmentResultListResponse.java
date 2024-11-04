package com.growplan.survey.dto.response;

import com.growplan.child.domain.UserChild;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class DevelopmentResultListResponse {

    private final List<DevelopmentResultResponse> developments;

    public static DevelopmentResultListResponse of(final UserChild userChild, final Map<String, Integer> scoreMap) {
        final List<DevelopmentResultResponse> responses = scoreMap.entrySet().stream()
                .map(entry -> DevelopmentResultResponse.of(
                        entry.getKey(),
                        entry.getValue(),
                        null
                ))
                .toList();

        return new DevelopmentResultListResponse(
                responses
        );
    }
}
