package com.growplan.record.controller;

import com.growplan.record.dto.request.RecordRequest;
import com.growplan.record.dto.response.RecordDetailResponse;
import com.growplan.record.dto.response.RecordListResponse;
import com.growplan.record.service.RecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/children/{childId}/records")
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<RecordListResponse> getRecords(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final RecordListResponse response = recordService.getRecords(userId, childId);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<RecordDetailResponse> getRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("recordId") final Long recordId
    ) {
        final RecordDetailResponse response = recordService.getRecord(userId, childId, recordId);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> saveRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @RequestBody @Valid final RecordRequest recordRequest
    ) {
        recordService.saveRecord(userId, childId, recordRequest);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<Void> updateRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("recordId") final Long recordId,
            @RequestBody @Valid final RecordRequest recordRequest
    ) {
        recordService.updateRecord(userId, childId, recordId, recordRequest);
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
