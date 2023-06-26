package com.tworld.celebring.event;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tworld.celebring.celeb.model.QViewCeleb;
import com.tworld.celebring.celeb.model.ViewCeleb;
import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.dto.QEventListDto;
import com.tworld.celebring.event.model.QEvent;
import com.tworld.celebring.event.model.QEventCeleb;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class EventServiceTest {

    @Autowired
    JPAQueryFactory queryFactory;

    @Test
    void getCurrentEventList() {
        QEvent e = new QEvent("e");
        QEventCeleb ec = new QEventCeleb("ec");
        QViewCeleb vw = new QViewCeleb("vw");

        List<EventListDto> events = queryFactory
                .select(new QEventListDto(
                        e.id,
                        e.name,
                        e.startDate,
                        e.endDate,
                        e.cafeName,
                        e.address
                ))
                .from(e)
                .where(Expressions.currentDate().between(e.startDate, e.endDate)
                        .and(e.deleteEntity.deleteYn.eq("N")))
                .offset(0)  // 시작 인덱스
                .limit(5)   // 개수
                .orderBy(e.startDate.asc())
                .fetch();

        for (EventListDto dto : events) {
            List<String> celebList = new ArrayList<>();

            List<ViewCeleb> celebs = queryFactory
                    .select(vw)
                    .from(vw)
                    .where(vw.id.in(
                            JPAExpressions
                                    .select(ec.id.celebId)
                                    .from(ec)
                                    .where(ec.id.eventId.eq(dto.getEventId()))
                    ))
                    .fetch();

            for (ViewCeleb c : celebs) {
                String name = c.getName();
                if (c.getGroupName() != null) name += "(" + c.getGroupName() + ")";
                celebList.add(name);
            }
            dto.setCeleb(celebList);
        }

        // 결과
        for (EventListDto dto : events) {
            System.out.print(dto.getEventId() + " | " + dto.getEventName() + " | " );
            for (String s: dto.getCeleb()) System.out.print(s + ", ");
            System.out.println();
        }
    }
}
