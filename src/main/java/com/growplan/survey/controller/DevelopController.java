package com.growplan.survey.controller;

import com.growplan.survey.dto.response.DevelopmentResultResponse;
import com.growplan.survey.dto.response.DevelopmentScaleSurveyResponse;
import com.growplan.survey.service.DevelopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/children/{childId}/developments")
public class DevelopController {

    private final DevelopService developService;

    @GetMapping
    public ResponseEntity<DevelopmentScaleSurveyResponse> getDevelopmentScaleAndSurveys(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final DevelopmentScaleSurveyResponse response = developService.getDevelopmentScalesAndSurveys(userId, childId);
        return ResponseEntity.ok().body(response);
    }

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
