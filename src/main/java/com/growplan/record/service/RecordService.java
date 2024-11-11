package com.growplan.record.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.common.exception.ImageException;
import com.growplan.image.domain.Image;
import com.growplan.image.domain.repository.ImageRepository;
import com.growplan.image.service.ImageService;
import com.growplan.record.domain.ChildRecord;
import com.growplan.record.domain.ChildRecordTag;
import com.growplan.record.domain.repository.RecordRepository;
import com.growplan.record.domain.repository.RecordTagRepository;
import com.growplan.record.dto.request.RecordRequest;
import com.growplan.record.dto.response.RecordListResponse;
import com.growplan.record.dto.response.RecordResponse;
import com.growplan.survey.domain.DevelopmentType;
import com.growplan.survey.domain.repository.DevelopmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.growplan.common.code.ExceptionCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final ChildRepository childRepository;
    private final DevelopmentTypeRepository developmentTypeRepository;
    private final RecordTagRepository recordTagRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    public RecordListResponse getRecords(final Long userId, final Long childId) {
        final List<ChildRecord> record = recordRepository.findRecordsByUserIdAndChildId(userId, childId);

        return RecordListResponse.of(record);
    }

    public RecordResponse getRecord(final Long userId, final Long childId, final Long recordId) {
        final ChildRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new BadRequestException(RECORD_NOT_FOUND));

        return RecordResponse.of(record);
    }

    public void saveRecord(final Long userId, final Long childId, final RecordRequest recordRequest, final List<MultipartFile> files) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        checkCountOfImage(files);

        final ChildRecord record = new ChildRecord(recordRequest.getScript(), userChild);
        final ChildRecord savedRecord = recordRepository.save(record);

        saveRecordImages(savedRecord, files);

        saveChildRecordTags(savedRecord, recordRequest.getDevelopmentTypes());
    }

    private void saveRecordImages(final ChildRecord record, final List<MultipartFile> files) {
        final List<String> imageUrls = saveImages(files);
        final List<Image> images = imageUrls.stream()
                .map(imageUrl -> new Image(imageUrl, record))
                .collect(Collectors.toList());
        imageRepository.saveAll(images);
    }

    private List<String> saveImages(final List<MultipartFile> files) {
        return files.stream()
                .map(file -> imageService.upload(file, "record"))
                .collect(Collectors.toList());
    }

    public void updateRecord(final Long userId, final Long childId, final Long recordId, final RecordRequest recordRequest, final List<MultipartFile> files) {
        final ChildRecord childRecord = recordRepository.findByChildIdAndRecordId(childId, recordId)
                .orElseThrow(() -> new BadRequestException(RECORD_NOT_FOUND));

        checkCountOfImage(files);

        childRecord.getImages().forEach(image -> imageService.deleteFileFromS3(image.getImageUrl()));
        recordRepository.delete(childRecord);

        final ChildRecord record = new ChildRecord(recordRequest.getScript(), childRecord.getUserChild());
        final ChildRecord savedRecord = recordRepository.save(record);

        saveRecordImages(savedRecord, files);

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
                .orElseThrow(() -> new BadRequestException(RECORD_NOT_FOUND));

        childRecord.getImages().forEach(image -> imageService.deleteFileFromS3(image.getImageUrl()));
        recordRepository.delete(childRecord);
    }

    private void checkCountOfImage(final List<MultipartFile> files) {
        if (files.size() > 5) {
            throw new ImageException(EXCEEDED_MAX_IMAGE_UPLOAD);
        }
    }
}
