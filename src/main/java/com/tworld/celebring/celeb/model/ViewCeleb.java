package com.tworld.celebring.celeb.model;

import com.querydsl.core.annotations.Immutable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Subselect;

@Entity(name = "vw_celeb")
@Immutable
@Subselect("select\n" +
        "    c.id as id,\n" +
        "             c.name,\n" +
        "             cl.group_id as group_id,\n" +
        "             (         select\n" +
        "                           c.name\n" +
        "                       from\n" +
        "                           celeb c\n" +
        "                       where\n" +
        "                               c.id = group_id     ) as group_name\n" +
        "from\n" +
        "    celeb c\n" +
        "        left join\n" +
        "    (\n" +
        "        select *\n" +
        "        from celeb_link\n" +
        "        group by member_id\n" +
        "    ) cl\n" +
        "    on c.id = cl.member_id\n" +
        "where c.delete_yn = 'N'")
@Getter
@NoArgsConstructor
public class ViewCeleb {
    @Id
    private Long id;
    private String name;
    private Long groupId;
    private String groupName;
}
