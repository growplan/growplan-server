package com.growplan.survey.controller;

import com.growplan.survey.dto.request.ChildSurveyRequest;
import com.growplan.survey.dto.request.ChildSurveyUpdateRequest;
import com.growplan.survey.dto.response.SurveyDetailListResponse;
import com.growplan.survey.dto.response.SurveyListResponse;
import com.growplan.survey.service.SurveyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
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
            @RequestBody @Valid final ChildSurveyRequest childSurveyRequest
    ) {
        // TOOD 수정 필요
        surveyService.saveChildSurvey(userId, childId, childSurveyRequest);
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
