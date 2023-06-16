package com.tworld.celebring.celeb.model;

import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.common.model.DeleteEntity;
import com.tworld.celebring.common.model.UpdateEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "celeb_like")
public class CelebLike {
    @EmbeddedId
    private CelebLikeId id;

    @NonNull
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Builder
    public CelebLike(Long userId, Long celebId) {
        this.id = CelebLikeId.builder().userId(userId).celebId(celebId).build();
    }
}
