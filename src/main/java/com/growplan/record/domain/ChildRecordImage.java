package com.growplan.record.domain;

import com.growplan.image.domain.Image;
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
@DiscriminatorValue("CHILD_RECORD")
@NoArgsConstructor(access = PROTECTED)
public class ChildRecordImage extends Image {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "child_record_id")
    private ChildRecord childRecord;

    public ChildRecordImage(
            final String imageUrl,
            final ChildRecord childRecord
    ) {
        super(imageUrl);
        this.childRecord = childRecord;
    }
}
