package com.tworld.celebring.event.model;

import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.common.model.DeleteEntity;
import com.tworld.celebring.common.model.UpdateEntity;
import com.tworld.celebring.event.dto.EventUpdateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.sql.Delete;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "event")
@DynamicUpdate
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull private String name;
    @NonNull @Temporal(TemporalType.DATE)
    private Date startDate;
    @NonNull @Temporal(TemporalType.DATE)
    private Date endDate;

    @NonNull private String cafeName;
    @NonNull private String address;
    @NonNull @Column(name = "map_x")
    private String mapX;           // 경도
    @NonNull @Column(name = "map_y")
    private String mapY;           // 위도

    @NonNull private String openingTime;
    @NonNull private String sns;

    @Embedded
    CreateEntity createEntity = new CreateEntity();
    @Embedded
    UpdateEntity updateEntity = new UpdateEntity();
    @Embedded
    DeleteEntity deleteEntity = new DeleteEntity();


    @Builder
    public Event(String name, Date startDate, Date endDate,
                 String cafeName, String address, String mapX, String mapY,
                 String openingTime, String sns, Long userId) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cafeName = cafeName;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
        this.openingTime = openingTime;
        this.sns = sns;
        this.createEntity = CreateEntity.builder().createBy(userId).createAt(LocalDateTime.now()).build();
        this.deleteEntity = DeleteEntity.builder().deleteYn("N").build();
    }

    public void update(EventUpdateDto dto, Long userId) {
        this.name = dto.getName();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.cafeName = dto.getCafeName();
        this.address = dto.getAddress();
        this.mapX = dto.getMapX();
        this.mapY = dto.getMapY();
        this.openingTime = dto.getOpeningTime();
        this.sns = dto.getSns();
        this.updateEntity = UpdateEntity.builder().updateBy(userId).updateAt(LocalDateTime.now()).build();
    }
}
