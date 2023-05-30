package com.tworld.celebring.celeb.model;

import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.common.model.DeleteEntity;
import com.tworld.celebring.common.model.UpdateEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "celeb_like")
public class CelebLike {
    @Id
    private Long userId;
    @Id
    private Long celebId;

    @NonNull @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
}
