package com.growplan.record.domain;

import com.growplan.survey.domain.DevelopmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DiscriminatorValue("CHILD_RECORD")
@NoArgsConstructor(access = PROTECTED)
public class ChildRecordTag extends Tag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private ChildRecord childRecord;

    public ChildRecordTag(final DevelopmentType developmentType, final ChildRecord childRecord) {
        super(developmentType);
        this.childRecord = childRecord;
    }
}
