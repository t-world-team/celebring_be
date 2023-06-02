package com.tworld.celebring.reivew.model;

import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.common.model.DeleteEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long eventId;
    @NonNull private String content;
    private String imageUrl;

    @Embedded
    CreateEntity createEntity;
    @Embedded
    DeleteEntity deleteEntity;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

}
