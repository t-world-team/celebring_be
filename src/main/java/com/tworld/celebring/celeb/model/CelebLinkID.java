package com.tworld.celebring.celeb.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class CelebLinkID implements Serializable {
    private Long groupId;
    private Long memberId;
}
