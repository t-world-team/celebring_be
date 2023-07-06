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
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Transactional
public class EventServiceTest {

    @Autowired
    JPAQueryFactory queryFactory;
    @Autowired
    EntityManager em;

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

    @Test
    @DisplayName("셀럽의 이벤트 달력")
    void eventCalendar() {
        Long searchCelebId = 3l;
        String day = "2023-06";

        QEvent e = new QEvent("e");
        QEventCeleb ec = new QEventCeleb("ec");

        String nativeQuery = """
                WITH RECURSIVE CTE  AS (
                    SELECT DATE_FORMAT( :startdate, '%Y-%m-%d') AS DT FROM DUAL
                    UNION ALL
                    SELECT DATE_ADD(DT, INTERVAL 1 DAY) FROM CTE
                    WHERE DT < DATE_FORMAT( :enddate, '%Y-%m-%d')
                )
                SELECT date_format(DT,'%Y-%m-%d') AS TDATE
                FROM CTE""";
        Map<String, Integer> map = new HashMap<>();

        // 1. 셀럽의 이벤트 목록을 조회한다.
        List<EventListDto> events = queryFactory
                .select(new QEventListDto(
                        e.id,
                        e.startDate,
                        e.endDate
                ))
                .from(e)
                .join(ec).on(e.id.eq(ec.id.eventId).and(ec.id.celebId.eq(searchCelebId)))
                .where(e.deleteEntity.deleteYn.eq("N"))
                .fetch();

        for (EventListDto dto : events) {
            System.out.println(dto.getEventId() + " | " + dto.getStartDate() + " | " + dto.getEndDate());

            // 2. 각 이벤트의 운영 기간을 조회한다.
            List<String> list = em.createNativeQuery(nativeQuery)
                    .setParameter("startdate", dto.getStartDate())
                    .setParameter("enddate", dto.getEndDate())
                    .getResultList();

            for (String str: list) {
                if (day.equals(str.substring(0,7))) { // 해당 달인 경우에만 수행
                    if (map.containsKey(str)) {
                        map.put(str, map.get(str) + 1);
                    } else {
                        map.put(str, 1);
                    }
                }
            }
        }

        for (String key: map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }

    }
}
