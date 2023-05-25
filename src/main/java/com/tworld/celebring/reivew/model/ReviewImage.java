package com.tworld.celebring.reivew.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "review_image")
public class ReviewImage {
    @EmbeddedId
    private ReviewImageID reviewImageID;

    @NonNull
    private int order;
}
