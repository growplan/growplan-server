package com.growplan.record.dto.response;

import com.growplan.record.domain.ChildRecord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class RecordResponse {

    private final Long id;
    private final String script;
    private final LocalDate recordedDate;
    private final List<String> developmentTypes;
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
