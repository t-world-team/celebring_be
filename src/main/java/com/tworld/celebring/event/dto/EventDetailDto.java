package com.tworld.celebring.event.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class EventDetailDto {
    private int liked;

    private Long eventId;
    private String eventName;
    private Date startDate;
    private Date endDate;
    private String cafeName;
    private String address;
    private String mapX;
    private String mapY;
    private String openingTime;
    private String twitter;

    private int writer;

    @QueryProjection
    public EventDetailDto(Long eventId, String eventName, Date startDate, Date endDate,
                          String cafeName, String address, String mapX, String mapY, String openingTime, String sns, Integer liked, Integer writer) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.cafeName = cafeName;
        this.address = address;
        this.mapX = mapX;
        this.mapY = mapY;
        this.openingTime = openingTime;
        this.twitter = getTwitter(sns);
        this.liked = liked == null ? 0 : 1;
        this.writer = writer == null ? 0 : 1;
    }

    public int getLiked() {
        return liked;
    }

    private String getTwitter(String url) {
        Pattern urlPattern = Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$");
        Matcher m = urlPattern.matcher(url);
        if (m.matches()) return m.group(7);
        return null;
    }
}
