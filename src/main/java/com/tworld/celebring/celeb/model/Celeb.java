package com.tworld.celebring.celeb.model;

import com.tworld.celebring.common.model.CreateEntity;
import com.tworld.celebring.common.model.DeleteEntity;
import com.tworld.celebring.common.model.UpdateEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "celeb")
public class Celeb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull @Temporal(TemporalType.DATE)
    private Date eventDate;

    @NonNull private String profileImage;

    private String keywords;

    @Embedded
    CreateEntity createEntity;
    @Embedded
    UpdateEntity updateEntity;
    @Embedded
    DeleteEntity deleteEntity;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private List<CelebLink> memberLink;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private List<CelebLink> subCeleb;

    @OneToOne
    @JoinColumn(name = "id")
    private CelebGroupName groupName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "celebId")
    private List<CelebLike> likes;

}
