package com.tworld.celebring.user.model;

import com.tworld.celebring.common.model.DeleteEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@NoArgsConstructor
@Entity(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull @Column(unique = true)
    private String email;
    @NonNull private String name;
    private String imageUrl;
    @NonNull @Column(unique = true)
    private String oauthId;
    @NonNull private String role;

    @CreatedDate
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Embedded
    DeleteEntity deleteEntity;

    @Builder
    public User(String oauthId, String name, String email) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.role = "ROLE_USER";
    }

    public void update(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
