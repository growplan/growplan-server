package com.growplan.post.entity;

import com.growplan.center.domain.Scrap;
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
@DiscriminatorValue("POST")
@NoArgsConstructor(access = PROTECTED)
public class PostScrap extends Scrap {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostScrap(
            final User user,
            final Post post
    ) {
        super(user);
        this.post = post;
    }
}
