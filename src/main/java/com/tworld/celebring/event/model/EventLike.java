package com.tworld.celebring.event.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@IdClass(EventLikePk.class)
@Table(name = "event_like")
public class EventLike {
    @Id
    private Long userId;
    @Id
    private Long celebId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
}
