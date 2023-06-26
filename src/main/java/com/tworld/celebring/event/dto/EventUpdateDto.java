package com.tworld.celebring.event.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EventUpdateDto {

    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String cafeName;
    private String address;
    private String mapX;
    private String mapY;
    private String openingTime;
    private String sns;
}
