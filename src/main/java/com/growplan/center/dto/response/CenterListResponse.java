package com.growplan.center.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CenterListResponse {

    private final List<CenterResponse> surveys;
    private final Long lastPageIndex;

    public static CenterListResponse of(final List<CenterResponse> centerResponses, final Long lastPageIndex) {
        return new CenterListResponse(
                centerResponses,
                lastPageIndex
        );
    }
}