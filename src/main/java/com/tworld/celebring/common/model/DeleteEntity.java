package com.tworld.celebring.common.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Embeddable
public class DeleteEntity {
    @Column(columnDefinition = "char")
    private String deleteYn;

    private Long deleteBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;
}
