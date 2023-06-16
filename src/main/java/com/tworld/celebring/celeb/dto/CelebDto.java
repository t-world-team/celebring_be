package com.tworld.celebring.celeb.dto;

import com.tworld.celebring.celeb.model.Celeb;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
public class CelebDto {
    private Long id;
    private String name;
    private Date eventDate;
    private String profileImage;
    private String groupName;
    private int like = 0;

    public CelebDto(Long id, String name, Date eventDate, String profileImage) {
        this.id = id;
        this.name = name;
        this.eventDate = eventDate;
        this.profileImage = profileImage;
    }

    @Builder
    public CelebDto(Celeb celeb, int like) {
        this.id = celeb.getId();
        this.name = celeb.getName();
        this.eventDate = celeb.getEventDate();
        this.profileImage = celeb.getProfileImage();
        if(celeb.getGroupName() != null) {
            this.groupName = celeb.getGroupName().getName();
        }
        this.like = like;
    }
}
