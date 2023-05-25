package com.tworld.celebring.reivew.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity
@IdClass(ReviewImagePK.class)
@Table(name = "review_image")
public class ReviewImage {
    @Id
    private Long reviewId;

    @Id
    private Long imageId;

    @NonNull
    private int order;
}
