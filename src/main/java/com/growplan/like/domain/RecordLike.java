package com.growplan.like.domain;

import com.growplan.common.BaseEntity;
import com.growplan.record.domain.ChildRecord;
import com.growplan.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class RecordLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "record_id")
    private ChildRecord childRecord;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public RecordLike(
            final User user,
            final ChildRecord childRecord
    ) {
        this.user = user;
        this.childRecord = childRecord;
    }
}
