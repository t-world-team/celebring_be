package com.tworld.celebring.event.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity(name = "event_celeb")
public class EventCeleb {
    @EmbeddedId
    private EventCelebId id;

    @Builder
    public EventCeleb(Long eventId, Long celebId) {
        this.id = EventCelebId.builder().eventId(eventId).celebId(celebId).build();
    }
}
