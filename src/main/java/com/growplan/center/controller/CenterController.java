package com.growplan.center.controller;

import com.growplan.center.dto.response.CenterListResponse;
import com.growplan.center.service.CenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Center-Controller", description = "센터 API 엔드포인트")
@RequestMapping("/centers")
public class CenterController {

    private final CenterService centerService;

    @Operation(summary = "센터 전체 조회", description = "센터를 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "센터 전체 조회에 성공했습니다.")
    @Parameter(name = "page", description = "페이지 번호 (기본값: 1)", example = "1")
    @Parameter(name = "size", description = "페이지 크기 (기본값: 6)", example = "10")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "centerTag", description = "센터 태그 (언어, 놀이, 심리, 감각통합, 인지, 미술, 사회성, 특수체육, ABA, 운동, 음악, 작업, 행동) / 적용 안하면 모든 센터 태그 조회", example = "언어")
    @Parameter(name = "province", description = "지역 (전체 조회 시 null 전달하면 됨)", example = "서울, 경기")
    @Parameter(name = "city", description = "도시 (전체 조회 시 null 전달하면 됨)", example = "노원구, 평택시")
    @Parameter(name = "neighborhood", description = "동 (전체 조회 시 null 전달하면 됨)", example = "논현동, 개포동")
    @GetMapping
    public ResponseEntity<CenterListResponse> getCenters(
            @PageableDefault(page = 1, size = 6) final Pageable pageable,
            @RequestParam(value = "userId", required = false) final Long userId,
            @RequestParam(value = "centerTags", required = false) final List<String> centerTags,
            @RequestParam(value = "province", required = false) final String province,
            @RequestParam(value = "city", required = false) final String city,
            @RequestParam(value = "neighborhood", required = false) final String neighborhood
    ) {
        final CenterListResponse centerListResponse = centerService.getCentersByPage(pageable, centerTags, province, city, neighborhood, userId);
        return ResponseEntity.ok().body(centerListResponse);
    }
}
