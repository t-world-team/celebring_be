package com.tworld.celebring.user.model;

import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.common.model.DeleteEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull @Column(unique = true)
    private Long email;
    @NonNull private String name;
    private String imageUrl;
    @NonNull @Column(unique = true)
    private String oauthId;
    @NonNull private String role;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Embedded
    DeleteEntity deleteEntity;
}
