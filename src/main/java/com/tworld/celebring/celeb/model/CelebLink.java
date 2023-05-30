package com.tworld.celebring.celeb.model;

import com.tworld.celebring.common.model.CreateEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "celeb_link")
public class CelebLink {
    @Id
    private Long groupId;
    @Id
    private Long memberId;

    @Embedded
    CreateEntity createEntity;
}
