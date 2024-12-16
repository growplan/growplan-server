package com.growplan.post.domain;

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
@DiscriminatorValue("POST")
@NoArgsConstructor(access = PROTECTED)
public class PostImage extends Image {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public PostImage(
            final String imageUrl,
            final Post post
    ) {
        super(imageUrl);
        this.post = post;
    }
}
