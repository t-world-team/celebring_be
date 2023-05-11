package com.tworld.celebring.event.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class EventLikeID implements Serializable {
    private Long userId;
    private Long celebId;
}
