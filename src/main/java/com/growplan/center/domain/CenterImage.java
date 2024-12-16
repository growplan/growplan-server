package com.growplan.center.domain;

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
@DiscriminatorValue("CENTER")
@NoArgsConstructor(access = PROTECTED)
public class CenterImage extends Image {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    public CenterImage(
            final String imageUrl,
            final Center center
    ) {
        super(imageUrl);
        this.center = center;
    }
}
