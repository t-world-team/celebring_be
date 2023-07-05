package com.tworld.celebring.event.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class EventCalendarDto {

    private String day;
    private int count;

    @Builder
    public EventCalendarDto(String day, int count) {
        this.day = day;
        this.count = count;
    }
}
