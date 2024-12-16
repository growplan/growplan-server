package com.growplan.post.entity;

import com.growplan.record.domain.Tag;
import com.growplan.survey.domain.DevelopmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DiscriminatorValue("POST")
@NoArgsConstructor(access = PROTECTED)
public class PostTag extends Tag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public PostTag(final DevelopmentType developmentType, final Post post) {
        super(developmentType);
        this.post = post;
    }
}
