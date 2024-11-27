package com.growplan.survey.controller;

import com.growplan.survey.dto.response.DevelopmentResultResponse;
import com.growplan.survey.dto.response.DevelopmentScaleSurveyResponse;
import com.growplan.survey.service.DevelopService;
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
@Tag(name = "Develop-Controller", description = "발달 API 엔드포인트")
@RequestMapping("/users/{userId}/children/{childId}/developments")
public class DevelopController {

    private final DevelopService developService;

    @Operation(summary = "발달 척도와 설문 조회", description = "발달 척도와 설문을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "발달 척도와 설문 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @GetMapping
    public ResponseEntity<DevelopmentScaleSurveyResponse> getDevelopmentScaleAndSurveys(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final DevelopmentScaleSurveyResponse response = developService.getDevelopmentScalesAndSurveys(userId, childId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "발달 설문 결과 조회", description = "발달 설문 결과를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "발달 설문 결과 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(name = "developmentType", description = "발달 영역 (GM, LM, CG, LG, SC, SH)", required = true, example = "1")
    @GetMapping("/{developmentType}")
    public ResponseEntity<DevelopmentResultResponse> getDevelopmentResult(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("developmentType") final String developmentType
    ) {
        final DevelopmentResultResponse response = developService.getDevelopmentResult(userId, childId, developmentType);
        return ResponseEntity.ok().body(response);
    }
}
