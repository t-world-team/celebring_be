package com.tworld.celebring.event.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity(name = "event_celeb")
public class EventCeleb {
    @Id
    private Long eventId;
    @Id
    private Long celebId;
}
