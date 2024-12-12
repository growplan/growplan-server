package com.growplan.center.dto.response;

import com.growplan.center.domain.Center;
import com.growplan.survey.domain.ChildSurvey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CenterListResponse {

    private final List<CenterResponse> surveys;
    private final Long lastPageIndex;

    public static CenterListResponse of(final List<Center> centers, final Long lastPageIndex) {
        final List<CenterResponse> centerResponses = centers.stream()
                .map(center -> CenterResponse.of(center))
                .collect(Collectors.toList());

        return new CenterListResponse(
                centerResponses,
                lastPageIndex
        );
    }
}