package com.growplan.child.dto.response;

import com.growplan.child.domain.UserChild;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ChildResponse {

    @Schema(description = "아이 아이디", example = "1")
    private final Long id;

    @Schema(description = "아이 이름", example = "김동동")
    private final String name;

    @Schema(description = "아이 생년월일 (YYYY-MM-DD 형식)", example = "2012-08-08")
    private final String birthdate;

    @Schema(description = "아이 성별 (예: 남자 또는 여자)", example = "여자")
    private final String gender;

    @Schema(description = "신생아 키(cm)", example = "50.0")
    private final Double bornHeight;

    @Schema(description = "신생아 몸무게(kg)", example = "3.2")
    private final Double bornWeight;

    @Schema(description = "이른둥이 여부 (true: 이른둥이 O, false: 이른둥이 X)", example = "false")
    private final Boolean isPremature;

    @Schema(description = "출생 당시 주수, 없을 시 null 반환", example = "40.0")
    private final Double birthWeeks;

    public static ChildResponse of(final UserChild userChild) {
        return new ChildResponse(
                userChild.getId(),
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