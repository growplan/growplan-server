package com.growplan.survey.domain;

import com.growplan.child.domain.UserChild;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class SurveyResult {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_child_id", nullable = false)
    private UserChild userChild;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "development_type_id", nullable = false)
    private DevelopmentType developmentType;

    @Column(nullable = false)
    private Integer score;

    @Column(nullable = false)
    private Boolean isRisk;
}
