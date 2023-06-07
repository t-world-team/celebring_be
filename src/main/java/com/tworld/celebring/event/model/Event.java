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
@Entity(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull private String name;
    @NonNull @Temporal(TemporalType.DATE)
    private Date startDate;
    @NonNull @Temporal(TemporalType.DATE)
    private Date endDate;
    private String address;
    private String openingTime;
    private String sns;
    private String headerImageUrl;

    @Embedded
    CreateEntity createEntity;
    @Embedded
    UpdateEntity updateEntity;
    @Embedded
    DeleteEntity deleteEntity;
}
