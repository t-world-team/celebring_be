package com.tworld.celebring.event.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "event_like")
public class EventLike {
    @EmbeddedId
    private EventLikeID eventLikeID;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
}
