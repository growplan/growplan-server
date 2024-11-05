package com.growplan.child.domain;

import com.growplan.child.domain.type.GenderType;
import com.growplan.common.BaseEntity;
import com.growplan.record.domain.ChildRecord;
import com.growplan.survey.domain.ChildSurvey;
import com.growplan.survey.domain.SurveyResult;
import com.growplan.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class UserChild extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String birthdate;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private GenderType gender;

    @Column(nullable = false)
    private Double bornHeight;

    @Column(nullable = false)
    private Double bornWeight;

    @Column(nullable = false)
    private Boolean isPremature;

    private Double birthWeeks;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "userChild", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChildRecord> childRecords = new ArrayList<>();

    @OneToMany(mappedBy = "userChild", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChildSurvey> childSurveys = new ArrayList<>();

    @OneToMany(mappedBy = "userChild", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<SurveyResult> surveyResults = new ArrayList<>();

    public UserChild(
            final String name,
            final String birthdate,
            final GenderType gender,
            final Double bornHeight,
            final Double bornWeight,
            final Boolean isPremature,
            final Double birthWeeks,
            final User user
    ) {
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.bornHeight = bornHeight;
        this.bornWeight = bornWeight;
        this.isPremature = isPremature;
        this.birthWeeks = birthWeeks;
        this.user = user;
    }

    public void updateUserChild(
            final String name,
            final String birthdate,
            final GenderType gender,
            final Double bornHeight,
            final Double bornWeight,
            final Boolean isPremature,
            final Double birthWeeks
    ) {
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.bornHeight = bornHeight;
        this.bornWeight = bornWeight;
        this.isPremature = isPremature;
        this.birthWeeks = birthWeeks;
    }
}
