package com.growplan.survey.domain;

import com.growplan.record.domain.ChildRecordTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class DevelopmentType {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2)
    private String type;

    @OneToMany(mappedBy = "developmentType")
    private List<ChildRecordTag> childRecordTags;

    @OneToMany(mappedBy = "developmentType")
    private List<SurveyGroup> surveyGroups;
}
