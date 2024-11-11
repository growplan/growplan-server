package com.growplan.image.domain;

import com.growplan.record.domain.ChildRecord;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "child_record_id")
    private ChildRecord childRecord;

    public Image(
            final String imageUrl,
            final ChildRecord childRecord
    ) {
        this.imageUrl = imageUrl;
        this.childRecord = childRecord;
    }
}
