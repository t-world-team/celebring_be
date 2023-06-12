package com.tworld.celebring.event.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.Date;

@Data
public class EventDetailDto {
    private boolean liked;

    private Long eventId;
    private String eventName;
    private Date startDate;
    private Date endDate;
    private String cafeName;
    private String address;
    private String mapX;
    private String mapY;
    private String openingTime;
    private String sns;

    @QueryProjection
    public EventDetailDto(Long eventId, String eventName, Date startDate, Date endDate,
                          String cafeName, String address, String mapX, String mapY, String openingTime, String sns, Integer liked) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cafeName = cafeName;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
        this.openingTime = openingTime;
        this.sns = sns;
        this.liked = liked == null ? false : true;
    }

    public boolean getLiked() {
        return liked;
    }
}
