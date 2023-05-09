package com.tworld.celebring.reivew.model;

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
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long eventId;

    @NonNull
    private String explain;

    private Long imageId;

    @NonNull
    private Long createBy;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @Column(columnDefinition = "char")
    private String deleteYn;

    private Long deleteBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;
}
