package com.tworld.celebring.event.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class EventListDto {
    private Long eventId;
    private String eventName;
    private Date startDate;
    private Date endDate;
    private String cafeName;
    private String address;
    private List<String> celeb;

    @QueryProjection
    public EventListDto(Long eventId, String eventName, Date startDate, Date endDate, String cafeName, String address) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cafeName = cafeName;
        this.address = address;
    }
}
