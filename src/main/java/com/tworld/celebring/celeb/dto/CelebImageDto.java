package com.tworld.celebring.celeb.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class CelebImageDto {
    private String imageUrl;
    private Integer seq;

    @Builder
    public CelebImageDto(String imageUrl, Integer seq) {
        this.imageUrl = imageUrl;
        this.seq = seq;
    }
}
