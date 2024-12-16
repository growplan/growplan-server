package com.growplan.post.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRequest {

    @NotBlank(message = "게시물 제목을 입력해주세요.")
    @Schema(description = "게시물 제목", example = "아이가 공을 가지고 놀아요.")
    private String title;

    @NotNull(message = "아이 개월수를 입력해주세요.")
    @Schema(description = "아이 개월수", example = "39")
    private Integer months;

    @NotBlank(message = "게시물 본문을 입력해주세요.")
    @Schema(description = "게시물 본문", example = "아이가 공놀이를 하며 소근육 발달에 도움을 받고 있습니다.")
    private String content;

    @NotEmpty(message = "발달 영역을 하나 이상 선택해주세요.")
    @Schema(description = "발달 영역 리스트 (GM, LM, CG, LG, SC, SH 중 선택)",
            example = "[\"GM\", \"LM\"]",
            allowableValues = {"GM", "LM", "CG", "LG", "SC", "SH"})
    private List<@Pattern(regexp = "GM|LM|CG|LG|SC|SH", message = "유효한 발달 영역을 선택해주세요.") String> developmentTypes;

    @NotEmpty(message = "태그를 하나 이상 선택해주세요.")
    @Schema(
            description = "태그 리스트 (도움이 필요해요, 나와 비슷한 부모님을 찾아요, 해결했어요, 추천해요, 궁금해요, 함께 이야기해요)",
            example = "[\"도움이 필요해요\", \"추천해요\"]",
            allowableValues = {
                    "도움이 필요해요",
                    "나와 비슷한 부모님을 찾아요",
                    "해결했어요",
                    "추천해요",
                    "궁금해요",
                    "함께 이야기해요"
            }
    )
    private List<@Pattern(
            regexp = "도움이 필요해요|나와 비슷한 부모님을 찾아요|해결했어요|추천해요|궁금해요|함께 이야기해요",
            message = "유효한 태그를 선택해주세요."
    ) String> tags;
}
