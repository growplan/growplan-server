package com.growplan.record.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.record.domain.ChildRecord;
import com.growplan.record.domain.ChildRecordTag;
import com.growplan.record.domain.repository.RecordRepository;
import com.growplan.record.domain.repository.RecordTagRepository;
import com.growplan.record.dto.request.RecordRequest;
import com.growplan.record.dto.response.RecordDetailResponse;
import com.growplan.record.dto.response.RecordListResponse;
import com.growplan.survey.domain.DevelopmentType;
import com.growplan.survey.domain.repository.DevelopmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.growplan.common.code.ExceptionCode.NOT_FOUND_RECORD;
import static com.growplan.common.code.ExceptionCode.NOT_FOUND_USER_CHILD;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final ChildRepository childRepository;
    private final DevelopmentTypeRepository developmentTypeRepository;
    private final RecordTagRepository recordTagRepository;

    public RecordListResponse getRecords(final Long userId, final Long childId) {
        final List<ChildRecord> record = recordRepository.findRecordsByUserIdAndChildId(userId, childId);

        return RecordListResponse.of(record);
    }

    public RecordDetailResponse getRecord(final Long userId, final Long childId, final Long recordId) {
        final ChildRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_RECORD));

        return RecordDetailResponse.of(record);
    }

    public void saveRecord(final Long userId, final Long childId, final RecordRequest recordRequest) {
        // TODO 사진 저장 로직 필요
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_USER_CHILD));

        final ChildRecord record = new ChildRecord(recordRequest.getScript(), userChild);
        final ChildRecord savedRecord = recordRepository.save(record);

        saveChildRecordTags(savedRecord, recordRequest.getDevelopmentTypes());
    }

    public void updateRecord(final Long userId, final Long childId, final Long recordId, final RecordRequest recordRequest) {
        final ChildRecord childRecord = recordRepository.findByChildIdAndRecordId(childId, recordId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_RECORD));

        recordRepository.delete(childRecord);

        final ChildRecord record = new ChildRecord(recordRequest.getScript(), childRecord.getUserChild());
        final ChildRecord savedRecord = recordRepository.save(record);

        saveChildRecordTags(savedRecord, recordRequest.getDevelopmentTypes());
    }

    private void saveChildRecordTags(final ChildRecord savedRecord, final List<String> selectedTypes) {
        List<DevelopmentType> developmentTypes = developmentTypeRepository.findByTypeIn(selectedTypes);
        List<ChildRecordTag> childRecordTags = new ArrayList<>();

        for (DevelopmentType developmentType : developmentTypes) {
            childRecordTags.add(new ChildRecordTag(savedRecord, developmentType));
        }

        recordTagRepository.saveAll(childRecordTags);
    }

    public void deleteRecord(final Long userId, final Long childId, final Long recordId) {
        final ChildRecord childRecord = recordRepository.findByChildIdAndRecordId(childId, recordId)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_RECORD));

        recordRepository.delete(childRecord);
    }
}
