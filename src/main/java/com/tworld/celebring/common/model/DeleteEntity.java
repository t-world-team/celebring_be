package com.tworld.celebring.common.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Embeddable
@NoArgsConstructor
public class DeleteEntity {
    @NonNull
    @Column(columnDefinition = "char")
    private String deleteYn;

    private Long deleteBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;

    @Builder
    public DeleteEntity(String deleteYn, Long deleteBy, Date deleteAt) {
        this.deleteYn = deleteYn;
        this.deleteBy = deleteBy;
        this.deleteAt = deleteAt;
    }
}
