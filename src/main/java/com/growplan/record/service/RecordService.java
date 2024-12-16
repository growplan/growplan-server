package com.growplan.record.service;

import com.growplan.child.domain.UserChild;
import com.growplan.child.domain.repository.ChildRepository;
import com.growplan.common.exception.BadRequestException;
import com.growplan.image.domain.Image;
import com.growplan.image.domain.repository.ImageRepository;
import com.growplan.image.service.ImageService;
import com.growplan.record.domain.ChildRecord;
import com.growplan.record.domain.ChildRecordImage;
import com.growplan.record.domain.ChildRecordTag;
import com.growplan.record.domain.repository.RecordRepository;
import com.growplan.record.domain.repository.TagRepository;
import com.growplan.record.dto.request.RecordRequest;
import com.growplan.record.dto.response.RecordListResponse;
import com.growplan.record.dto.response.RecordResponse;
import com.growplan.survey.domain.DevelopmentType;
import com.growplan.survey.domain.repository.DevelopmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.growplan.common.code.ExceptionCode.RECORD_NOT_FOUND;
import static com.growplan.common.code.ExceptionCode.USER_CHILD_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final ChildRepository childRepository;
    private final DevelopmentTypeRepository developmentTypeRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    public RecordListResponse getRecords(final Long userId, final Long childId, final String sort, final String developmentType, final boolean isLiked) {
        List<ChildRecord> records = recordRepository.findRecordsByUserIdAndChildId(userId, childId);

        records = filterByDevelopmentType(records, developmentType);
        // records = filterByDate(records, startDate, endDate);
        records = sortRecords(records, sort);
        records = filterByIsLiked(records, sort, isLiked);

        return RecordListResponse.of(records);
    }

    private List<ChildRecord> filterByDevelopmentType(List<ChildRecord> records, String developmentType) {
        if (developmentType == null || developmentType.trim().isEmpty()) {
            return records;
        }

        return records.stream()
                .filter(record -> record.getRecordTags() != null &&
                        record.getRecordTags().stream()
                                .anyMatch(tag -> tag.getDevelopmentType().getType().equals(developmentType)))
                .collect(Collectors.toList());
    }

    private List<ChildRecord> filterByDate(final List<ChildRecord> records, final String startDate, final String endDate) {
        if ((startDate == null || startDate.isEmpty()) && (endDate == null || endDate.isEmpty())) {
            return records;
        }

        final LocalDate start = LocalDate.parse(startDate);
        final LocalDate end = LocalDate.parse(endDate);

        return records.stream()
                .filter(record -> {
                    final LocalDateTime createdAt = record.getCreatedAt();
                    return !createdAt.isBefore(start.atStartOfDay()) && !createdAt.isAfter(end.atTime(23, 59, 59));
                })
                .collect(Collectors.toList());
    }

    private List<ChildRecord> sortRecords(final List<ChildRecord> records, final String sort) {
        if ("asc".equalsIgnoreCase(sort)) {
            records.sort(Comparator.comparing(ChildRecord::getCreatedAt));
        } else {
            records.sort(Comparator.comparing(ChildRecord::getCreatedAt).reversed());
        }
        return records;
    }

    private List<ChildRecord> filterByIsLiked(final List<ChildRecord> records, final String sort, boolean isLiked) {
        if (isLiked) {
            records.sort((r1, r2) -> {
                final boolean r1HasLike = r1.isLiked();
                final boolean r2HasLike = r2.isLiked();

                if (r1HasLike && !r2HasLike) {
                    return -1;
                }
                if (!r1HasLike && r2HasLike) {
                    return 1;
                }

                return "asc".equalsIgnoreCase(sort) ? r1.getCreatedAt().compareTo(r2.getCreatedAt()) : r2.getCreatedAt().compareTo(r1.getCreatedAt());
            });
        }
        return records;
    }

    public RecordResponse getRecord(final Long userId, final Long childId, final Long recordId) {
        final ChildRecord record = recordRepository.findById(recordId)
                .orElseThrow(() -> new BadRequestException(RECORD_NOT_FOUND));

        return RecordResponse.of(record);
    }

    public void saveRecord(final Long userId, final Long childId, final RecordRequest recordRequest, final List<MultipartFile> files) {
        final UserChild userChild = childRepository.findByUserIdAndChildId(userId, childId)
                .orElseThrow(() -> new BadRequestException(USER_CHILD_NOT_FOUND));

        imageService.checkCountOfImage(files);

        final ChildRecord record = new ChildRecord(recordRequest.getScript(), userChild, false);
        final ChildRecord savedRecord = recordRepository.save(record);

        saveRecordImages(savedRecord, files);

        saveChildRecordTags(savedRecord, recordRequest.getDevelopmentTypes());
    }

    private void saveRecordImages(final ChildRecord record, final List<MultipartFile> files) {
        if (files != null) {
            final List<String> imageUrls = uploadImages(files);
            final List<Image> images = imageUrls.stream()
                    .map(imageUrl -> new ChildRecordImage(imageUrl, record))
                    .collect(Collectors.toList());
            imageRepository.saveAll(images);
        }
    }

    private List<String> uploadImages(final List<MultipartFile> files) {
        return files.stream()
                .map(file -> imageService.upload(file, "record"))
                .collect(Collectors.toList());
    }

    public void updateRecord(final Long userId, final Long childId, final Long recordId, final RecordRequest recordRequest, final List<MultipartFile> files) {
        final ChildRecord childRecord = recordRepository.findByChildIdAndRecordId(childId, recordId)
                .orElseThrow(() -> new BadRequestException(RECORD_NOT_FOUND));

        imageService.checkCountOfImage(files);

        childRecord.getChildRecordImages().forEach(image -> imageService.deleteFileFromS3(image.getImageUrl()));
        recordRepository.delete(childRecord);

        final ChildRecord record = new ChildRecord(recordRequest.getScript(), childRecord.getUserChild(), childRecord.isLiked());
        final ChildRecord savedRecord = recordRepository.save(record);

        saveRecordImages(savedRecord, files);

        saveChildRecordTags(savedRecord, recordRequest.getDevelopmentTypes());
    }

    private void saveChildRecordTags(final ChildRecord savedRecord, final List<String> selectedTypes) {
        List<DevelopmentType> developmentTypes = developmentTypeRepository.findByTypeIn(selectedTypes);
        List<ChildRecordTag> childRecordTags = new ArrayList<>();

        for (DevelopmentType developmentType : developmentTypes) {
            childRecordTags.add(new ChildRecordTag(developmentType, savedRecord));
        }

        tagRepository.saveAll(childRecordTags);
    }

    public void deleteRecord(final Long userId, final Long childId, final Long recordId) {
        final ChildRecord childRecord = recordRepository.findByChildIdAndRecordId(childId, recordId)
                .orElseThrow(() -> new BadRequestException(RECORD_NOT_FOUND));

        childRecord.getChildRecordImages().forEach(image -> imageService.deleteFileFromS3(image.getImageUrl()));
        recordRepository.delete(childRecord);
    }

    public void toggleLike(final Long userId, final Long recordId) {
        final ChildRecord record = recordRepository.findByUserIdAndRecordId(userId, recordId)
                .orElseThrow(() -> new BadRequestException(RECORD_NOT_FOUND));

        record.toggleIsLiked();
        recordRepository.save(record);
    }
}
