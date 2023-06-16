package com.tworld.celebring.celeb.dto;

import com.tworld.celebring.celeb.model.CelebLike;
import lombok.Builder;
import lombok.Data;

@Data
public class CelebLikeDto {
    private Long userId;
    private Long celebId;

    public CelebLikeDto(Long userId, Long celebId) {
        this.userId = userId;
        this.celebId = celebId;
    }

}
