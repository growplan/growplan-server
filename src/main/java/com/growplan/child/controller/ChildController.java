package com.growplan.child.controller;

import com.growplan.child.dto.request.ChildRequest;
import com.growplan.child.dto.response.ChildListResponse;
import com.growplan.child.dto.response.ChildResponse;
import com.growplan.child.service.ChildService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Child-Controller", description = "아이 API 엔드포인트")
@RequestMapping("/users/{userId}/childs")
public class ChildController {

    private final ChildService childService;

    @Operation(summary = "아이 전체 조회", description = "아이를 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "아이 전체 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @GetMapping
    public ResponseEntity<ChildListResponse> getChildren(@PathVariable("userId") final Long userId) {
        final ChildListResponse childListResponse = childService.getChildren(userId);
        return ResponseEntity.ok().body(childListResponse);
    }

    @Operation(summary = "아이 저장", description = "아이를 저장합니다.")
    @ApiResponse(responseCode = "204", description = "아이 저장에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(description = "아이 저장 정보", required = true)
    @PostMapping
    public ResponseEntity<Void> saveChild(
            @PathVariable("userId") final Long userId,
            @RequestBody @Valid final ChildRequest childRequest
    ) {
        childService.saveChild(userId, childRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "아이 단일 조회", description = "아이를 단일 조회합니다.")
    @ApiResponse(responseCode = "200", description = "아이 단일 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @GetMapping("/{childId}")
    public ResponseEntity<ChildResponse> getChild(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final ChildResponse childResponse = childService.getChild(userId, childId);
        return ResponseEntity.ok().body(childResponse);
    }

    @Operation(summary = "아이 업데이트", description = "아이를 업데이트합니다.")
    @ApiResponse(responseCode = "204", description = "아이 업데이트에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(description = "아이 업데이트 정보", required = true)
    @PutMapping("/{childId}")
    public ResponseEntity<Void> updateChild(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @RequestBody @Valid final ChildRequest childRequest
    ) {
        childService.updateChild(userId, childId, childRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "아이 삭제", description = "아이를 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "아이 삭제에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @DeleteMapping("/{childId}")
    public ResponseEntity<Void> deleteChild(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        childService.deleteChild(userId, childId);
        return ResponseEntity.noContent().build();
    }
}
