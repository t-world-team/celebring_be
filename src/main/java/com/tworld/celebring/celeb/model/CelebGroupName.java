package com.tworld.celebring.celeb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Getter
@Immutable
@Subselect("select cl.member_id as id, c.name from celeb_link cl \r\n" +
            " left outer join celeb c on c.id = cl.group_id \r\n" +
            "where cl.member_id not in (select distinct group_id from celeb_link) \r\n" +
            "  and cl.group_id not in (select distinct member_id from celeb_link)"
)
@Entity(name = "celeb_group_name")
public class CelebGroupName {
    @Id
    private Long id;

    private String name;
}
