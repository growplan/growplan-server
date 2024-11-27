package com.growplan.login.domain;

import lombok.Getter;

@Getter
public class Accessor {

    private final Long userId;

    public Accessor(
            final Long userId
    ) {
        this.userId = userId;
    }

    public static Accessor user(final Long userId) {
        return new Accessor(userId);
    }
}