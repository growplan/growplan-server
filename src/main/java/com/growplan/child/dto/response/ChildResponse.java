package com.growplan.child.dto.response;

import com.growplan.child.domain.UserChild;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChildResponse {

    private final String name;
    private final String birthdate;
    private final String gender;
    private final Double bornHeight;
    private final Double bornWeight;
    private final Boolean isPremature;
    private final Double birthWeeks;

    public static ChildResponse of(final UserChild userChild) {
        return new ChildResponse(
                userChild.getName(),
                userChild.getBirthdate(),
                userChild.getGender().getCode(),
                userChild.getBornHeight(),
                userChild.getBornWeight(),
                userChild.getIsPremature(),
                userChild.getBirthWeeks()
        );
    }
}
