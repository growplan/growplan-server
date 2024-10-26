package com.growplan.child.domain;

import com.growplan.child.domain.type.GenderType;
import com.growplan.common.BaseEntity;
import com.growplan.record.domain.ChildRecord;
import com.growplan.survey.domain.ChildSurvey;
import com.growplan.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class UserChild extends BaseEntity {

    @Id
    @GeneratedValue
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userChild", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChildRecord> childRecords;

    @OneToMany(mappedBy = "userChild", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChildSurvey> childSurveys;
}
