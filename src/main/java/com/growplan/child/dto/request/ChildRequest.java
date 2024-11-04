package com.growplan.child.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildRequest {

    @NotBlank(message = "아이 이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "아이 생년월일을 입력해주세요.")
    private String birthdate;

    @NotBlank(message = "아이 성별을 입력해주세요.")
    private String gender;

    @NotBlank(message = "신생아 키를 입력해주세요.")
    private Double bornHeight;

    @NotBlank(message = "신생아 몸무게를 입력해주세요.")
    private Double bornWeight;

    @NotBlank(message = "이른둥이 여부를 입력해주세요.")
    private Boolean isPremature;

    private Double birthWeeks;
}
