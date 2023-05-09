package com.tworld.celebring.event.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class EventLikePk implements Serializable {
    private Long userId;
    private Long celebId;
}
