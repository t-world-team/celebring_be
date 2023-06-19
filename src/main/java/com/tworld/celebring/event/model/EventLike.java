package com.tworld.celebring.event.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "event_like")
public class EventLike {
    @EmbeddedId
    private EventLikeId id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    public EventLike(Long userId, Long eventId) {
        this.id = EventLikeId.builder().userId(userId).eventId(eventId).build();
    }
}
