package com.growplan.user.dto.response;

import com.growplan.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserListResponse {

    private final List<UserResponse> users;

    public static final UserListResponse of(final List<User> users) {
        final List<UserResponse> userResponses = users.stream()
                .map(user -> UserResponse.of(user))
                .toList();

        return new UserListResponse(userResponses);
    }
}
