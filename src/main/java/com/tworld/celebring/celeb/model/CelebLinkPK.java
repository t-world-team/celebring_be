package com.tworld.celebring.celeb.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CelebLinkPK implements Serializable {
    private Long groupId;
    private Long memberId;
}
