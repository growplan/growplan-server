package com.growplan.survey.controller;

import com.growplan.survey.dto.response.DevelopmentResultListResponse;
import com.growplan.survey.service.DevelopService;
import lombok.RequiredArgsConstructor;
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
    public DevelopmentResultListResponse getAllDevelopmentResult(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId
    ) {
        developService.getAllDevelopmentResults(userId, childId);
    }

    @GetMapping("/{developmentType}")
    public DevelopmentResultListResponse getDevelopmentResult(
            @PathVariable("userId") final Long userId,
            @PathVariable("childId") final Long childId,
            @PathVariable("developmentType") final String developmentType
    ) {
        developService.getDevelopmentResult(userId, childId, developmentType);

    }
}
