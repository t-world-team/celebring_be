package com.tworld.celebring.event.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class EventListDto {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String cafeName;
    private String address;
    private List<String> celeb;

    @QueryProjection
    public EventListDto(Long id, String name, Date startDate, Date endDate, String cafeName, String address) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cafeName = cafeName;
        this.address = address;
    }
}
