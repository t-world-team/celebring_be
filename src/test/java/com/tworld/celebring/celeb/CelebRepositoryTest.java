package com.tworld.celebring.celeb;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tworld.celebring.celeb.model.QViewCeleb;
import com.tworld.celebring.celeb.model.ViewCeleb;
import com.tworld.celebring.event.model.QEvent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Transactional
public class CelebRepositoryTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    @DisplayName("셀럽 뷰 조회")
    void howsEvent() {
        QViewCeleb vw = new QViewCeleb("vw");

        Long searchCelebId = 1l;

        List<ViewCeleb> celebs = queryFactory
                .select(vw)
                .from(vw)
                .where(vw.id.eq(searchCelebId))
                .fetch();

        for (ViewCeleb a: celebs) {
            System.out.println(a.getId() + " | " + a.getName() + " | " + a.getGroupId() + " | " + a.getGroupName());
        }
    }
}
