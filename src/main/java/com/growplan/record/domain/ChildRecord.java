package com.growplan.record.domain;

import com.growplan.child.domain.UserChild;
import com.growplan.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.HashSet;
import java.util.Set;

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

    @Column(nullable = false)
    private boolean isLiked;

    @OneToMany(mappedBy = "childRecord", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ChildRecordTag> recordTags = new HashSet<>();

    @OneToMany(mappedBy = "childRecord", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ChildRecordImage> childRecordImages = new HashSet<>();

    public ChildRecord(final String script, final UserChild userChild, final boolean isLiked) {
        this.script = script;
        this.userChild = userChild;
        this.isLiked = isLiked;
    }

    public void toggleIsLiked() {
        this.isLiked = !this.isLiked();
    }
}
