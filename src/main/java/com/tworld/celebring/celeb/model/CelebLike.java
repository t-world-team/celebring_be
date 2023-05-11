package com.tworld.celebring.celeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "celeb_like")
public class CelebLike {
    @EmbeddedId
    private CelebLikeID celebLikeID;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    public static class CelebLink {
    }
}
