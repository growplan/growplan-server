package com.growplan.survey.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SurveyGroup {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "development_type_id", nullable = false)
    private DevelopmentType developmentType;

    @Column(nullable = false)
    private Integer minMonth;

    @Column(nullable = false)
    private Integer maxMonth;

    @OneToMany(mappedBy = "surveyGroup", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Survey> surveys = new ArrayList<>();

    @OneToMany(mappedBy = "surveyGroup", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Feedback> feedbacks = new ArrayList<>();
}
