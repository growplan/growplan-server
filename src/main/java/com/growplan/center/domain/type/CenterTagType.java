package com.growplan.center.domain.type;

import com.growplan.common.exception.InvalidDomainException;
import lombok.Getter;

import java.util.Arrays;

import static com.growplan.common.code.ExceptionCode.CENTER_TAG_NOT_FOUND;

@Getter
public enum CenterTagType {

    LANGUAGE("언어"),
    PLAY("놀이"),
    PSYCHOLOGY("심리"),
    SENSORY_INTEGRATION("감각통합"),
    COGNITION("인지"),
    ART("미술"),
    SOCIAL_SKILLS("사회성"),
    SPECIAL_PHYSICAL_EDUCATION("특수체육"),
    ABA("ABA"),
    EXERCISE("운동"),
    MUSIC("음악"),
    WORK("작업"),
    BEHAVIOR("행동");

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
