package com.growplan.record.controller;

import com.growplan.record.dto.request.RecordRequest;
import com.growplan.record.dto.response.RecordListResponse;
import com.growplan.record.dto.response.RecordResponse;
import com.growplan.record.service.RecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Record-Controller", description = "기록 API 엔드포인트")
@RequestMapping("/users/{userId}/children/{childId}/records")
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<RecordListResponse> getRecords(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @RequestParam(value = "sort", defaultValue = "desc") final String sort,
            @RequestParam(value = "startDate", required = false) final String startDate,
            @RequestParam(value = "endDate", required = false) final String endDate,
            @RequestParam(value = "developmentType", required = false) final String developmentType
    ) {
        final RecordListResponse response = recordService.getRecords(userId, childId, sort, startDate, endDate, developmentType);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<RecordResponse> getRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("recordId") final Long recordId
    ) {
        final RecordResponse response = recordService.getRecord(userId, childId, recordId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> saveRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @RequestPart("requestDto") @Valid final RecordRequest recordRequest,
            @RequestPart("files") List<MultipartFile> files
    ) {
        recordService.saveRecord(userId, childId, recordRequest, files);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<Void> updateRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("recordId") final Long recordId,
            @RequestPart("requestDto") @Valid final RecordRequest recordRequest,
            @RequestPart("files") List<MultipartFile> files
    ) {
        recordService.updateRecord(userId, childId, recordId, recordRequest, files);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("recordId") final Long recordId
    ) {
        recordService.deleteRecord(userId, childId, recordId);
        return ResponseEntity.noContent().build();
    }
}
