package com.growplan.like.controller;

import com.growplan.like.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Like-Controller", description = "좋아요 API 엔드포인트")
@RequestMapping("/users/{userId}/records")
public class LikeController {

    private final LikeService likeService;

    @Operation(summary = "발달 일지 좋아요 토글", description = "발달 일지에 좋아요를 토글합니다. 존재하면 삭제, 없으면 추가합니다.")
    @ApiResponse(responseCode = "204", description = "발달 일지 좋아요 토글에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "recordId", description = "기록 아이디", required = true, example = "1")
    @PostMapping("/{recordId}/like")
    public ResponseEntity<Void> toggleLike(
            @PathVariable("userId") final Long userId,
            @PathVariable("recordId") final Long recordId
    ) {
        likeService.toggleLike(userId, recordId);
        return ResponseEntity.noContent().build();
    }
}
