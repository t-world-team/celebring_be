package com.tworld.celebring.celeb.model;

import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.common.model.DeleteEntity;
import com.tworld.celebring.common.model.UpdateEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "celeb")
public class Celeb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull @Temporal(TemporalType.DATE)
    private Date eventDate;

    @NonNull private String imageUrl;

    private String keywords;

    private String keywords;

    @Embedded
    CreateEntity createEntity;
    @Embedded
    UpdateEntity updateEntity;
    @Embedded
    DeleteEntity deleteEntity;
}
