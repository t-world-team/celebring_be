package com.tworld.celebring.celeb.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CelebInfoDto {
    private String name;
    private Date eventDate;
    private List<CelebImageDto> imageList;

    @Builder
    public CelebInfoDto(String name, Date eventDate, List<CelebImageDto> imageList) {
        this.name = name;
        this.eventDate = eventDate;
        this.imageList = imageList;
    }
}
