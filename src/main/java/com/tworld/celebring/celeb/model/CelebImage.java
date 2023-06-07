package com.tworld.celebring.celeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@NoArgsConstructor
@Entity(name = "celeb_image")
public class CelebImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private Long celebId;

    @NonNull private String imageUrl;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ImageType type;

    private Integer seq;  // type = background 일때만 필요
}
