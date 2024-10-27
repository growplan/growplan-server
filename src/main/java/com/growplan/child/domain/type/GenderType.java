package com.growplan.child.domain.type;

import com.growplan.common.exception.InvalidDomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

import static com.growplan.common.code.ExceptionCode.INVALID_GENDER_TYPE;

@Getter
@AllArgsConstructor
public enum GenderType {

    GIRL("여아"), BOY("남아"), OTHER("기타");
    
    private final String code;

    public static GenderType of(final String code) {
        return Arrays.stream(GenderType.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new InvalidDomainException(INVALID_GENDER_TYPE));
    }
}
