package com.growplan.center.domain;

import com.growplan.user.domain.User;
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
public class CenterScrap extends Scrap {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    public CenterScrap(
            final User user,
            final Center center
    ) {
        super(user);
        this.center = center;
    }
}
