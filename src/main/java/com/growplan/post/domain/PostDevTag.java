package com.growplan.post.domain;

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
@DiscriminatorValue("POST")
@NoArgsConstructor(access = PROTECTED)
public class PostDevTag extends Tag {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostDevTag(final DevelopmentType developmentType, final Post post) {
        super(developmentType);
        this.post = post;
    }
}
