package com.growplan.survey.controller;

import com.growplan.survey.dto.request.ChildSurveyListRequest;
import com.growplan.survey.dto.request.ChildSurveyUpdateRequest;
import com.growplan.survey.dto.response.SurveyDetailListResponse;
import com.growplan.survey.dto.response.SurveyListResponse;
import com.growplan.survey.service.SurveyService;
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
@Tag(name = "Survey-Controller", description = "설문 API 엔드포인트")
@RequestMapping("/users/{userId}/children/{childId}/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    @Operation(summary = "아이 설문 전체 조회", description = "아이 설문을 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "아이 설문 전체 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @GetMapping
    public ResponseEntity<SurveyListResponse> getChildSurveys(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final SurveyListResponse surveyListResponse = surveyService.getChildSurveys(userId, childId);
        return ResponseEntity.ok().body(surveyListResponse);
    }

    @Operation(summary = "아이 설문 전체 조회", description = "아이 설문을 전체 조회합니다.")
    @ApiResponse(responseCode = "200", description = "아이 설문 전체 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @Parameter(name = "developmentType", description = "발달 영역 (GM, LM, CG, LG, SC, SH)", required = true, example = "GM")
    @GetMapping("/{developmentType}")
    public ResponseEntity<SurveyDetailListResponse> getSurveyDetail(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("developmentType") final String developmentType
    ) {
        final SurveyDetailListResponse response = surveyService.getSurveyDetail(userId, childId, developmentType);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "발달 척도와 설문 조회", description = "발달 척도와 설문을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "발달 척도와 설문 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @PostMapping
    public ResponseEntity<Void> saveChildSurveys(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @RequestBody @Valid final ChildSurveyListRequest childSurveyListRequest
    ) {
        surveyService.saveChildSurvey(userId, childId, childSurveyListRequest.getSurveys());
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "발달 척도와 설문 조회", description = "발달 척도와 설문을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "발달 척도와 설문 조회에 성공했습니다.")
    @Parameter(name = "userId", description = "유저 아이디", required = true, example = "1")
    @Parameter(name = "childId", description = "아이 아이디", required = true, example = "1")
    @PatchMapping("/{surveyId}")
    public ResponseEntity<Void> updateChildSurvey(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("surveyId") final Long surveyId,
            @RequestBody @Valid final ChildSurveyUpdateRequest childSurveyUpdateRequest

    ) {
        surveyService.updateChildSurvey(userId, childId, surveyId, childSurveyUpdateRequest);
        return ResponseEntity.noContent().build();
    }
}
