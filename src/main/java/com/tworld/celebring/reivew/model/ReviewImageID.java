package com.tworld.celebring.reivew.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class ReviewImageID implements Serializable {
    private Long reviewId;
    private Long imageId;
}
