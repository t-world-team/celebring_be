package com.tworld.celebring.common.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Embeddable
@NoArgsConstructor
public class UpdateEntity {
    private Long updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @Builder
    public UpdateEntity(Long updateBy, Date updateAt) {
        this.updateBy = updateBy;
        this.updateAt = updateAt;
    }
}
