package com.tworld.celebring.celeb.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class CelebLinkPk implements Serializable {
    private Long groupId;
    private Long memberId;
}
