package com.growplan.center.domain.type;

import com.growplan.common.exception.InvalidDomainException;
import lombok.Getter;

import java.util.Arrays;

import static com.growplan.common.code.ExceptionCode.CENTER_TAG_NOT_FOUND;

@Getter
public enum CenterTagType {

    LG("언어"),
    PL("놀이"),
    PS("심리"),
    SI("감각통합"),
    CO("인지"),
    AR("미술"),
    SO("사회성"),
    PE("특수체육"),
    AB("ABA"),
    EX("운동"),
    MU("음악"),
    OT("작업"),
    BH("행동");

    private final String name;

    CenterTagType(final String name) {
        this.name = name;
    }

    public static CenterTagType of(final String name) {
        return Arrays.stream(CenterTagType.values())
                .filter(py -> py.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new InvalidDomainException(CENTER_TAG_NOT_FOUND));
    }
}
