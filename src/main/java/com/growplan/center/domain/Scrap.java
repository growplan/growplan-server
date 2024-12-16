package com.growplan.center.domain;

import com.growplan.common.BaseEntity;
import com.growplan.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@Inheritance(strategy = SINGLE_TABLE)
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorColumn
public abstract class Scrap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Scrap(
            final User user
    ) {
        this.user = user;
    }
}
