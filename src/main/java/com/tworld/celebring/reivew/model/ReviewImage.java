package com.tworld.celebring.reivew.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity(name = "review_image")
public class ReviewImage {
    @Id
    private Long reviewId;
    @Id
    private Long imageId;

    @NonNull private Integer seq;
}
