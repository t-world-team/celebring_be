package com.tworld.celebring.celeb.model;

import com.querydsl.core.annotations.Immutable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;

@Entity(name = "vw_celeb")
@Immutable
@Subselect("select c.id as id, c.name,  cl.group_id as group_id, (\n" +
        "    select c.name from celeb c where c.id = group_id\n" +
        "    ) as group_name\n" +
        "from celeb c\n" +
        "         left join celeb_link cl on c.id = cl.member_id")
@Getter
@NoArgsConstructor
public class ViewCeleb {
    @Id
    private Long id;
    private String name;
    private Long groupId;
    private String groupName;
}
