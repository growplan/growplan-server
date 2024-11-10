package com.growplan.survey.controller;

import com.growplan.survey.dto.request.ChildSurveyListRequest;
import com.growplan.survey.dto.request.ChildSurveyUpdateRequest;
import com.growplan.survey.dto.response.SurveyDetailListResponse;
import com.growplan.survey.dto.response.SurveyListResponse;
import com.growplan.survey.service.SurveyService;
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

    @GetMapping
    public ResponseEntity<SurveyListResponse> getChildSurveys(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final SurveyListResponse surveyListResponse = surveyService.getChildSurveys(userId, childId);
        return ResponseEntity.ok().body(surveyListResponse);
    }

    @GetMapping("/{developmentType}")
    public ResponseEntity<SurveyDetailListResponse> getSurveyDetail(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("developmentType") final String developmentType
    ) {
        final SurveyDetailListResponse response = surveyService.getSurveyDetail(userId, childId, developmentType);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Void> saveChildSurveys(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @RequestBody @Valid final ChildSurveyListRequest childSurveyListRequest
    ) {
        surveyService.saveChildSurvey(userId, childId, childSurveyListRequest.getSurveys());
        return ResponseEntity.noContent().build();
    }

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
