package com.tworld.celebring.celeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@IdClass(CelebLikePk.class)
@Table(name = "celeb_like")
public class CelebLike {
    @Id
    private Long userId;
    @Id
    private Long celebId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    public static class CelebLink {
    }
}
