package com.growplan.record.dto.response;

import com.growplan.record.domain.ChildRecord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class RecordDetailResponse {

    private final Long id;
    private final String script;
    private final LocalDate recordedDate;
    private final List<String> imageUrls;
    private final List<String> developmentTypes;

    public static final RecordDetailResponse of(final ChildRecord record) {
        final List<String> developmentTypes = record.getRecordTags().stream()
                .map(recordTag -> recordTag.getDevelopmentType().getType())
                .toList();

        return new RecordDetailResponse(
                record.getId(),
                record.getScript(),
                record.getCreatedAt().toLocalDate(),
                null,
                developmentTypes
        );
    }
}
