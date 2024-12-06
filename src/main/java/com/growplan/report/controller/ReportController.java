package com.growplan.report.controller;

import com.growplan.report.dto.response.ReportListResponse;
import com.growplan.report.dto.response.SummaryResponse;
import com.growplan.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/users/{userId}/childs/{childId}/reports")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "발달 레포트 조회", description = "발달 레포트를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "발달 레포트 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @GetMapping
    public ResponseEntity<ReportListResponse> getReport(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final ReportListResponse reportListResponse = reportService.getMonthlyReport(userId, childId);
        return ResponseEntity.ok().body(reportListResponse);
    }

    @Operation(summary = "발달 유형별 발달 레포트 요약 조회", description = "발달 유형별 발달 레포트 요약을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "발달 유형별 발달 레포트 요약 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(name = "developmentType", description = "발달 영역 (GM, LM, CG, LG, SC, SH)", required = true, example = "1")
    @GetMapping("/{developmentType}")
    public ResponseEntity<SummaryResponse> getSummary(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("developmentType") final String developmentType
    ) {
        final SummaryResponse summaryResponse = reportService.getSummary(userId, childId, developmentType);
        return ResponseEntity.ok().body(summaryResponse);
    }
}
