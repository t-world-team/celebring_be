package com.tworld.celebring.event.model;

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
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long celebId;

    @NonNull
    private String name;

    @NonNull
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @NonNull
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private String address;

    private String openingTime;

    private String sns;

    private Long headerImageId;

    private Long mainImageId;

    private String explainNo1;

    private String explainNo2;

    private String explainNo3;

    @NonNull
    private Long createBy;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    private Long updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @Column(columnDefinition = "char")
    private String deleteYn;

    private Long deleteBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;

}
