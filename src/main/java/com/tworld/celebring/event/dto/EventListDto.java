package com.tworld.celebring.event.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import java.util.Date;

@Data
public class EventListDto {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String address;
    private String mainImageUrl;

    @QueryProjection
    public EventListDto(Long id, String name, Date startDate, Date endDate, String address, String mainImageUrl) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.address = address;
        this.mainImageUrl = mainImageUrl;
    }
}