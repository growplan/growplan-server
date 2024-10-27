package com.growplan.user.dto.response;

import com.growplan.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserResponse {

    private final Long id;
    private final String name;
    private final String birthdate;
    private final String email;
    private final String number;
    private final Integer isValid;

    public static UserResponse of(final User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getBirthdate(),
                user.getEmail(),
                user.getNumber(),
                user.getIsValid().getStatusCode()
        );
    }
}
