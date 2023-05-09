package com.tworld.celebring.celeb.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CelebLikePk implements Serializable {
    private Long userId;
    private Long celebId;
}
