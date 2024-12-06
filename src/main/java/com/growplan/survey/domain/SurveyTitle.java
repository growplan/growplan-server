package com.growplan.survey.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SurveyTitle {

    private final String title;

    private final String developmentType;
}
