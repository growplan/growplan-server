package com.growplan.survey.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChildSurveyElement {

    private String title;
    private ChildSurvey childSurveys;
}
