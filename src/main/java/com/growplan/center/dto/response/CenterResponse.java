package com.growplan.center.dto.response;

import com.growplan.center.domain.Center;
import com.growplan.center.domain.type.CenterTagType;
import com.growplan.image.domain.Image;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CenterResponse {

    @Schema(description = "센터 아이디", example = "1")
    private final Long centerId;

    @Schema(description = "센터 이름", example = "해냄공간")
    private final String name;

    @Schema(description = "센터 위치", example = "경기 구리시 건원대로 42 삼원골드프라자 417호")
    private final String location;

    @Schema(description = "센터 태그 리스트", example = "[\"언어\", \"운동\"]")
    private final List<String> tags;

    @Schema(description = "이미지 URL 리스트", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
    private final List<String> images;

    @Schema(description = "스크랩 여부", example = "false")
    private final Boolean isScraped;

    public static CenterResponse of(final Center center, final Boolean isScraped) {
        final List<String> tags = center.getCenterDevTags().stream()
                .map(centerTag -> CenterTagType.valueOf(centerTag.getDevelopmentType().getType()).getName())
                .collect(Collectors.toList());

        final List<String> images = center.getCenterImages().stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());

        return new CenterResponse(
                center.getId(),
                center.getName(),
                center.getLocation(),
                tags,
                images,
                isScraped
        );
    }
}