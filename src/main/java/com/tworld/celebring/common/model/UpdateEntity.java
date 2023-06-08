package com.tworld.celebring.common.model;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.util.Date;

@Embeddable
public class UpdateEntity {
    private Long updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;
}
