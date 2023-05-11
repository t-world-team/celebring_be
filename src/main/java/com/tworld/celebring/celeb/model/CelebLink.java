package com.tworld.celebring.celeb.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "celeb_link")
public class CelebLink {
    @EmbeddedId
    private CelebLinkID celebLinkID;

    @NonNull
    private Long createBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
}
