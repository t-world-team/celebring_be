package com.tworld.celebring.common.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Embeddable
@NoArgsConstructor
public class CreateEntity {
    @NonNull
    private Long createBy;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt;

    @Builder
    public CreateEntity(Long createBy, LocalDateTime createAt) {
        this.createBy = createBy;
        this.createAt = createAt;
    }
}
