package com.tworld.celebring.event.model;

import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.common.model.DeleteEntity;
import com.tworld.celebring.common.model.UpdateEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "event_explain")
public class EventExplain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull private Long eventId;
    @NonNull private String content;
    @NonNull private Integer seq;
}
