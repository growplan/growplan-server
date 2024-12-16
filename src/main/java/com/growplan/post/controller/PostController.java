package com.growplan.post.controller;

import com.growplan.post.dto.request.PostRequest;
import com.growplan.post.service.PostService;
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
@Tag(name = "Post-Controller", description = "커뮤니티 게시물 API 엔드포인트")
@RequestMapping("/users/{userId}/posts")
public class PostController {

    private final PostService postService;

    @Operation(summary = "커뮤니티 게시물 저장", description = "커뮤니티 게시물을 저장합니다.")
    @ApiResponse(responseCode = "204", description = "커뮤니티 게시물 저장에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(description = "커뮤니티 게시물 저장 정보", required = true)
    @Parameter(description = "업로드할 사진 파일들 (최대 5개)")
    @PostMapping
    public ResponseEntity<Void> saveRecord(
            @PathVariable("userId") final Long userId,
            @RequestPart("requestDto") @Valid final PostRequest postRequest,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        postService.savePost(userId, postRequest, files);
        return ResponseEntity.noContent().build();
    }
}
