package com.tworld.celebring.reivew.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewImagePK implements Serializable {
    private Long reviewId;
    private Long imageId;
}
