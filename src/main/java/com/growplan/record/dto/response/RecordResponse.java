package com.growplan.record.dto.response;

import com.growplan.record.domain.ChildRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class RecordResponse {

    @Schema(description = "발달 일지 아이디", example = "1")
    private final Long id;

    @Schema(description = "발달 관련 설명", example = "아이의 언어 발달이 빠르게 이루어졌습니다.")
    private final String script;

    @Schema(description = "발달 일지 기록 날짜", example = "2023-01-01")
    private final LocalDate recordedDate;

    @Schema(description = "발달 영역 리스트", example = "[\"GM\", \"LM\"]")
    private final List<String> developmentTypes;

    @Schema(description = "이미지 URL 리스트", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
    private final List<String> imageUrls;

    public static final RecordResponse of(final ChildRecord record) {
        final List<String> developmentTypes = record.getRecordTags().stream()
                .map(recordTag -> recordTag.getDevelopmentType().getType())
                .toList();

        final List<String> imageUrls = record.getImages().stream()
                .map(image -> image.getImageUrl())
                .toList();

        return new RecordResponse(
                record.getId(),
                record.getScript(),
                record.getCreatedAt().toLocalDate(),
                developmentTypes,
                imageUrls
        );
    }
}
