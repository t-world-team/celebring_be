package com.tworld.celebring.celeb.model;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Embeddable
@RequiredArgsConstructor
public class CelebLinkId implements Serializable {
    private Long groupId;
    private Long memberId;

    @Builder
    public CelebLinkId(Long groupId, Long memberId) {
        this.groupId = groupId;
        this.memberId = memberId;
    }
}
