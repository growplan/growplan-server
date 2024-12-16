package com.growplan.record.controller;

import com.growplan.record.dto.request.RecordRequest;
import com.growplan.record.dto.response.RecordListResponse;
import com.growplan.record.dto.response.RecordResponse;
import com.growplan.record.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/users/{userId}/childs/{childId}/records")
public class RecordController {

    private final RecordService recordService;

    @Operation(summary = "발달 일지 전체 조회", description = "발달 일지를 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "발달 일지 전체 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(name = "sort", description = "정렬 기준 (asc: 오름차순, desc: 내림차순)", example = "desc")
    // @Parameter(name = "startDate", description = "조회 시작일 (YYYY-MM-DD 형식)", example = "2023-01-01")
    // @Parameter(name = "endDate", description = "조회 종료일 (YYYY-MM-DD 형식)", example = "2023-12-31")
    @Parameter(name = "developmentType", description = "발달 영역 (GM, LM, CG, LG, SC, SH) / 적용 안하면 모든 발달 영역 조회", example = "GM")
    @Parameter(name = "isLiked", description = "좋아요 여부 (true: 좋아요 있음, false: 좋아요 없음)", example = "true")
    @GetMapping
    public ResponseEntity<RecordListResponse> getRecords(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @RequestParam(value = "sort", defaultValue = "desc") final String sort,
            // @RequestParam(value = "startDate", required = false) final String startDate,
            // @RequestParam(value = "endDate", required = false) final String endDate,
            @RequestParam(value = "developmentType", required = false) final String developmentType,
            @RequestParam(value = "isLiked", defaultValue = "false") final Boolean isLiked
    ) {
        final RecordListResponse response = recordService.getRecords(userId, childId, sort, developmentType, isLiked);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "발달 일지 단일 조회", description = "발달 일지를 단일 조회합니다.")
    @ApiResponse(responseCode = "200", description = "발달 일지 단일 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(name = "recordId", description = "기록 아이디", required = true, example = "1")
    @GetMapping("/{recordId}")
    public ResponseEntity<RecordResponse> getRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("recordId") final Long recordId
    ) {
        final RecordResponse response = recordService.getRecord(userId, childId, recordId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "발달 일지 저장", description = "발달 일지를 저장합니다.")
    @ApiResponse(responseCode = "204", description = "발달 일지 저장에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(description = "발달 일지 저장 정보", required = true)
    @Parameter(description = "업로드할 사진 파일들 (최대 5개)")
    @PostMapping
    public ResponseEntity<Void> saveRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @RequestPart("requestDto") @Valid final RecordRequest recordRequest,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        recordService.saveRecord(userId, childId, recordRequest, files);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "발달 일지 수정", description = "발달 일지를 수정합니다.")
    @ApiResponse(responseCode = "204", description = "발달 일지 수정에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(name = "recordId", description = "기록 아이디", required = true, example = "1")
    @Parameter(description = "발달 일지 수정 정보", required = true)
    @Parameter(description = "업로드할 사진 파일들 (최대 5개)")
    @PutMapping("/{recordId}")
    public ResponseEntity<Void> updateRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("recordId") final Long recordId,
            @RequestPart("requestDto") @Valid final RecordRequest recordRequest,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        recordService.updateRecord(userId, childId, recordId, recordRequest, files);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "발달 일지 삭제", description = "발달 일지를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "발달 일지 삭제에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(name = "recordId", description = "기록 아이디", required = true, example = "1")
    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("recordId") final Long recordId
    ) {
        recordService.deleteRecord(userId, childId, recordId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "발달 일지 좋아요 토글", description = "발달 일지에 좋아요를 토글합니다. 존재하면 삭제, 없으면 추가합니다.")
    @ApiResponse(responseCode = "204", description = "발달 일지 좋아요 토글에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(name = "recordId", description = "기록 아이디", required = true, example = "1")
    @PostMapping("/{recordId}/like")
    public ResponseEntity<Void> toggleLike(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("recordId") final Long recordId
    ) {
        recordService.toggleLike(userId, recordId);
        return ResponseEntity.noContent().build();
    }
}
