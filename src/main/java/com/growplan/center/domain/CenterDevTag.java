package com.growplan.center.domain;

import com.growplan.record.domain.Tag;
import com.growplan.survey.domain.DevelopmentType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DiscriminatorValue("CENTER")
@NoArgsConstructor(access = PROTECTED)
public class CenterDevTag extends Tag {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    public CenterDevTag(final DevelopmentType developmentType, final Center center) {
        super(developmentType);
        this.center = center;
    }
}
