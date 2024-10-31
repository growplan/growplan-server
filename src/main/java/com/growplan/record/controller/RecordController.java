package com.growplan.record.controller;

import com.growplan.record.dto.response.RecordListResponse;
import com.growplan.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/children/{childId}/records")
public class RecordController {

    private final RecordService recordService;

    @GetMapping
    public ResponseEntity<RecordListResponse> getSurveys(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final RecordListResponse response = recordService.getRecords(userId, childId);
        return ResponseEntity.ok().body(response);
    }
}
