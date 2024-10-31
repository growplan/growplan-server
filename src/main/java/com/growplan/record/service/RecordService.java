package com.growplan.record.service;

import com.growplan.record.domain.ChildRecord;
import com.growplan.record.domain.repository.RecordRepository;
import com.growplan.record.dto.response.RecordListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public RecordListResponse getRecords(final Long userId, final Long childId) {
        final List<ChildRecord> record = recordRepository.findByUserIdAndChildId(userId, childId);

        return RecordListResponse.of(record);
    }
}
