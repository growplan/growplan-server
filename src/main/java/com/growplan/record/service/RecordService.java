package com.growplan.record.service;

import com.growplan.common.exception.BadRequestException;
import com.growplan.record.domain.ChildRecord;
import com.growplan.record.domain.repository.RecordRepository;
import com.growplan.record.dto.response.RecordDetailResponse;
import com.growplan.record.dto.response.RecordListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.growplan.common.code.ExceptionCode.NOT_FOUND_RECORD;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public RecordListResponse getRecords(final Long userId, final Long childId) {
        final List<ChildRecord> record = recordRepository.findRecordsByUserIdAndChildId(userId, childId);

        return RecordListResponse.of(record);
    }

    public RecordDetailResponse getRecord(final Long userId, final Long childId, final Long recordId) {
        final ChildRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_RECORD));

        return RecordDetailResponse.of(record);
    }
}
