package com.growplan.login.dto.response;

import com.growplan.user.domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpResponse {

    private final Long id;
    private final String name;
    private final String birthdate;
    private final String email;
    private final String number;
    private final Integer isValid;

    public static SignUpResponse of(final User user) {
        return new SignUpResponse(
                user.getId(),
                user.getName(),
                user.getBirthdate(),
                user.getEmail(),
                user.getNumber(),
                user.getIsValid().getStatusCode()
        );
    }
}
