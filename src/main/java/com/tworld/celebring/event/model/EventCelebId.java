package com.tworld.celebring.event.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
public class EventCelebId implements Serializable {
    private Long eventId;
    private Long celebId;

    @Builder
    public EventCelebId(Long eventId, Long celebId) {
        this.eventId = eventId;
        this.celebId = celebId;
    }
}
