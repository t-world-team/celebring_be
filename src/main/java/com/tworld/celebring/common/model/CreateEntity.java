package com.tworld.celebring.common.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Embeddable
public class CreateEntity {
    @NonNull
    private Long createBy;

    @NonNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
}
