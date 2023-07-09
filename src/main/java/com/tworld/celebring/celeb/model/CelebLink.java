package com.tworld.celebring.celeb.model;

import com.tworld.celebring.common.model.CreateEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity(name = "celeb_link")
public class CelebLink {
    @EmbeddedId
    private CelebLinkId id;

    @Embedded
    CreateEntity createEntity;

    @Builder
    public CelebLink(Long groupId, Long memberId, Long userId) {
        this.id = CelebLinkId.builder().groupId(groupId).memberId(memberId).build();
        this.createEntity = CreateEntity.builder().createBy(userId).createAt(LocalDateTime.now()).build();
    }
}
