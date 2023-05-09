package com.tworld.celebring.common.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@DynamicInsert  // insertn 시 null 인 필드 제외
@Table(name = "image_file")
public class ImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String oriName;

    @NonNull
    private String path;

    @NonNull
    private Long createBy;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(columnDefinition = "char")
    private String deleteYn;

    private Long deleteBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;
}
