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

    @NonNull private String openingTime;
    @NonNull private String sns;
    private String imageUrl;

    @NonNull private String cafeName;
    @NonNull private String address;
    @NonNull @Column(name = "map_x")
    private String mapX;
    @NonNull @Column(name = "map_y")
    private String mapY;

    @Embedded
    CreateEntity createEntity;
    @Embedded
    UpdateEntity updateEntity;
    @Embedded
    DeleteEntity deleteEntity;
}
