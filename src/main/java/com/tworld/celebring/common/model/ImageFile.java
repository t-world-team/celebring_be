package com.tworld.celebring.common.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "image_file")
public class ImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String oriName;
    private String path;

    @Embedded
    CreateEntity createEntity;
    @Embedded
    DeleteEntity deleteEntity;
}
