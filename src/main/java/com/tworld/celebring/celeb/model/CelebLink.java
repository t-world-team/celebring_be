package com.tworld.celebring.celeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@IdClass(CelebLinkPk.class)
@Table(name = "celeb_link")
public class CelebLink {
    @Id
    private Long groupId;
    @Id
    private Long memberId;

    @NonNull
    private Long createBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
}
