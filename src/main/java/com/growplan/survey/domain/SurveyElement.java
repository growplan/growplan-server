package com.growplan.survey.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SurveyElement {

    private Long id;
    private String developmentType;
    private String script;
}
