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
    private String oauthId;
    @NonNull private String role;

    @CreatedDate
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Column(columnDefinition = "char")
    private String deleteYn;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteAt;

    @Builder
    public User(String oauthId) {
        this.oauthId = oauthId;
        this.role = "ROLE_USER";
    }

}
