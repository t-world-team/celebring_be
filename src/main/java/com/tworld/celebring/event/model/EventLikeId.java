package com.tworld.celebring.event.model;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class EventLikeId implements Serializable {
    private Long userId;
    private Long eventId;

    @Builder
    public EventLikeId(Long userId, Long eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

}
