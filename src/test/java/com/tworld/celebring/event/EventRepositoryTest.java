package com.tworld.celebring.event;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tworld.celebring.celeb.model.*;
import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.dto.QEventListDto;
import com.tworld.celebring.event.model.EventCeleb;
import com.tworld.celebring.event.model.QEvent;
import com.tworld.celebring.event.model.QEventCeleb;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class EventRepositoryTest {

    @Autowired JPAQueryFactory queryFactory;

    @Test
    @DisplayName("현재 이벤트 목록")
    void currentEvent() {
        QEvent e = new QEvent("e");

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
                .where(Expressions.currentDate().between(e.startDate, e.endDate))
                .offset(0)  // 시작 인덱스
                .limit(5)   // 개수
                .orderBy(e.startDate.asc())
                .fetch();

//        assertThat(events.size()).isEqualTo(2);
        for (EventListDto dto : events) {
            System.out.println(dto.getEventId() + " | " + dto.getEventName() + " | " + dto.getStartDate() + "~" + dto.getEndDate());
        }
    }

    @Test
    @DisplayName("이벤트의 셀럽 조회")
    void howsEvent() {
        QEventCeleb ec = new QEventCeleb("ec");
        QViewCeleb vw = new QViewCeleb("vw");

        Long searchEventId = 1l;

        List<ViewCeleb> celebs = queryFactory
                .select(vw)
                .from(vw)
                .where(vw.id.in(
                        JPAExpressions
                                .select(ec.celebId)
                                .from(ec)
                                .where(ec.eventId.eq(searchEventId))
                ))
                .fetch();

        for (ViewCeleb a: celebs) {
            String name = a.getName();
            if (a.getGroupName() != null) name += "(" + a.getGroupName() + ")";
            System.out.println(name);
        }
    }

    @Test
    @DisplayName("셀럽의 현재 이벤트 목록")
    void currentEventByCeleb() {
        Long searchCelebId = 2l;

        QEvent e = new QEvent("e");
        QEventCeleb ec = new QEventCeleb("ec");

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
                .join(ec).on(e.id.eq(ec.eventId).and(ec.celebId.eq(searchCelebId)))
                .where(Expressions.currentDate().between(e.startDate, e.endDate))
                .offset(0)  // 시작 인덱스
                .limit(5)   // 개수
                .orderBy(e.startDate.asc())
                .fetch();

//        assertThat(events.size()).isEqualTo(2);
        for (EventListDto dto : events) {
            System.out.println(dto.getEventId() + " | " + dto.getEventName() + " | " + dto.getStartDate() + "~" + dto.getEndDate());
        }
    }

    @Test
    @DisplayName("셀럽의 예정 이벤트 목록")
    void upcomingEventByCeleb() {
        Long searchCelebId = 2l;

        QEvent e = new QEvent("e");
        QEventCeleb ec = new QEventCeleb("ec");

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
                .join(ec).on(e.id.eq(ec.eventId).and(ec.celebId.eq(searchCelebId)))
                .where(Expressions.currentDate().lt(e.startDate))
                .offset(0)  // 시작 인덱스
                .limit(10)   // 개수
                .orderBy(e.startDate.asc())
                .fetch();

        for (EventListDto dto : events) {
            System.out.println(dto.getEventId() + " | " + dto.getEventName() + " | " + dto.getStartDate() + "~" + dto.getEndDate());
        }
    }

    @Test
    @DisplayName("셀럽의 지난 이벤트 목록")
    void pastEventByCeleb() {
        Long searchCelebId = 2l;

        QEvent e = new QEvent("e");
        QEventCeleb ec = new QEventCeleb("ec");

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
                .join(ec).on(e.id.eq(ec.eventId).and(ec.celebId.eq(searchCelebId)))
                .where(Expressions.currentDate().gt(e.endDate))
                .offset(0)  // 시작 인덱스
                .limit(10)   // 개수
                .orderBy(e.endDate.asc())
                .fetch();

        for (EventListDto dto : events) {
            System.out.println(dto.getEventId() + " | " + dto.getEventName() + " | " + dto.getStartDate() + "~" + dto.getEndDate());
        }
    }
}
