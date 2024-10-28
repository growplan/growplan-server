package com.growplan.survey.controller;

import com.growplan.survey.dto.request.ChildSurveyUpdateRequest;
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
    public ResponseEntity<SurveyListResponse> getSurveys(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final SurveyListResponse surveyListResponse = surveyService.getSurvey(userId, childId);
        return ResponseEntity.ok().body(surveyListResponse);
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
