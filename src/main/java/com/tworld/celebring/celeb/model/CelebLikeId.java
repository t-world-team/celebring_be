package com.tworld.celebring.celeb.model;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Embeddable
@RequiredArgsConstructor
public class CelebLikeId implements Serializable {
    private Long userId;
    private Long celebId;

    @Builder
    public CelebLikeId(Long userId, Long celebId) {
        this.userId = userId;
        this.celebId = celebId;
    }
}
