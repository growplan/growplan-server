package com.growplan.survey.domain;

import com.growplan.child.domain.UserChild;
import com.growplan.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class ChildSurvey extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer status;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "child_id")
    private UserChild userChild;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public ChildSurvey(
            final Integer status,
            final UserChild userChild,
            final Survey survey
    ) {
        this.status = status;
        this.userChild = userChild;
        this.survey = survey;
    }

    public void updateChildSurvey(final Integer status) {
        this.status = status;
    }
}
