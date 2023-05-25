package com.tworld.celebring.celeb.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CelebLikePK implements Serializable {
    private Long userId;
    private Long celebId;
}
