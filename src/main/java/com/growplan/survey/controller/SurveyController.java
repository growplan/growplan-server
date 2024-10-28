package com.growplan.survey.controller;

import com.growplan.survey.dto.response.SurveyListResponse;
import com.growplan.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping("/{userId}/children/{childId}/surveys")
    public ResponseEntity<SurveyListResponse> getSurveys(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        final SurveyListResponse surveyListResponse = surveyService.getSurvey(userId, childId);
        return ResponseEntity.ok().body(surveyListResponse);
    }
}
