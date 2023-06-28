package com.tworld.celebring.event.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class EventAddDto {
    private List<Long> celebId;
    private String eventName;
    private Date startDate;
    private Date endDate;
    private String cafeName;
    private String address;
    private String mapX;
    private String mapY;
    private String openingTime;
    private String twitter;
}
