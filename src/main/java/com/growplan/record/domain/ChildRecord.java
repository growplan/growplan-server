package com.growplan.record.domain;

import com.growplan.child.domain.UserChild;
import com.growplan.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = PROTECTED)
public class ChildRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String script;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false, name = "child_id")
    private UserChild userChild;

    @OneToMany(mappedBy = "childRecord", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ChildRecordTag> recordTags = new ArrayList<>();

    public ChildRecord(final String script, final UserChild userChild) {
        this.script = script;
        this.userChild = userChild;
    }
}
