package com.growplan.record.dto.response;

import com.growplan.record.domain.ChildRecord;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class RecordListResponse {

    private final List<RecordResponse> records;

    public static RecordListResponse of(final List<ChildRecord> records) {
        final List<RecordResponse> responses = records.stream()
                .map(record -> RecordResponse.of(record))
                .toList();

        return new RecordListResponse(responses);
    }
}
