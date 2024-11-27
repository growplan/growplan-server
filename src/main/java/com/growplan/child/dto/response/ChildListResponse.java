package com.growplan.child.dto.response;

import com.growplan.child.domain.UserChild;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ChildListResponse {

    private final List<ChildResponse> children;

    public static final ChildListResponse of(final List<UserChild> children) {
        final List<ChildResponse> childResponses = children.stream()
                .map(child -> ChildResponse.of(child))
                .toList();

        return new ChildListResponse(childResponses);
    }
}
