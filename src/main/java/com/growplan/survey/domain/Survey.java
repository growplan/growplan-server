package com.growplan.survey.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Survey {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double validAge;

    @Column(nullable = false)
    private String script;

    @Column(nullable = false)
    private Double weight;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "development_type_id", nullable = false)
    private DevelopmentType developmentType;
}
