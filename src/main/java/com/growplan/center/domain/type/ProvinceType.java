package com.growplan.center.domain.type;

import com.growplan.common.exception.InvalidDomainException;
import lombok.Getter;

import java.util.Arrays;

import static com.growplan.common.code.ExceptionCode.PROVINCE_NOT_FOUND;

@Getter
public enum ProvinceType {

    SEOUL("서울"),
    GYEONGGI("경기"),

    BUSAN("부산"),
    DAEGU("대구"),
    INCHEON("인천"),
    GWANGJU("광주"),
    DAEJEON("대전"),
    ULSAN("울산"),

    SEJONG("세종"),
    GANGWON("강원"),
    CHUNGBUK("충북"),
    CHUNGNAM("충남"),
    JEONBUK("전북"),
    JEONNAM("전남"),
    GYEONGBUK("경북"),
    GYEONGNAM("경남"),
    JEJU("제주");

    private final String name;

    ProvinceType(final String name) {
        this.name = name;
    }

    public static ProvinceType of(final String name) {
        return Arrays.stream(ProvinceType.values())
                .filter(py -> py.getName().equals(name))
                .findAny()
                .orElseThrow(() -> new InvalidDomainException(PROVINCE_NOT_FOUND));
    }
}
