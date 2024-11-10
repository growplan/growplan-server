package com.growplan.report.controller;

import com.growplan.report.dto.response.ReportListResponse;
import com.growplan.report.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Report-Controller", description = "레포트 API 엔드포인트")
@RequestMapping("/users/{userId}/children/{childId}/reports")
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<ReportListResponse> getReports(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final ReportListResponse reportListResponse = reportService.getReports(userId, childId);
        return ResponseEntity.ok().body(reportListResponse);
    }
}
