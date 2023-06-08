package com.tworld.celebring.event;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.dto.QEventListDto;
import com.tworld.celebring.event.model.Event;
import com.tworld.celebring.event.model.QEvent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class EventRepositoryTest {

    @Autowired JPAQueryFactory queryFactory;

//    @Test
//    void startQueryDsl() {
//        QEvent e = new QEvent("e");
//
//        List<Event> events = queryFactory
//                .select(e)
//                .from(e)
//                .where(e.celebId.eq((long) 2))
//                .fetch();
//
//        assertThat(events.size()).isEqualTo(3);
//    }
//
//    @Test
//    @DisplayName("현재 이벤트 목록")
//    void currentEvent() {
//        QEvent e = new QEvent("e");
//
//        List<EventListDto> events = queryFactory
//                .select(new QEventListDto(
//                        e.id,
//                        e.name,
//                        e.startDate,
//                        e.endDate,
//                        e.address,
//                        e.mainImageUrl
//                ))
//                .from(e)
//                .where(Expressions.currentDate().between(e.startDate, e.endDate))
//                .offset(0)  // 시작 인덱스
//                .limit(5)   // 개수
//                .orderBy(e.startDate.asc())
//                .fetch();
//
////        assertThat(events.size()).isEqualTo(2);
//        for (EventListDto dto : events) {
//            System.out.println(dto.getId() + " | " + dto.getName() + " | " + dto.getStartDate() + "~" + dto.getEndDate());
//        }
//    }
//
//    @Test
//    @DisplayName("셀럽의 현재 이벤트 목록")
//    void currentEventByCeleb() {
//        Long searchCelebId = 2l;
//
//        QEvent e = new QEvent("e");
//
//        List<EventListDto> events = queryFactory
//                .select(new QEventListDto(
//                        e.id,
//                        e.name,
//                        e.startDate,
//                        e.endDate,
//                        e.address,
//                        e.mainImageUrl
//                ))
//                .from(e)
//                .where(
//                        e.celebId.eq(searchCelebId)
//                                .and(Expressions.currentDate().between(e.startDate, e.endDate))
//                )
//                .offset(0)  // 시작 인덱스
//                .limit(5)   // 개수
//                .orderBy(e.startDate.asc())
//                .fetch();
//
////        assertThat(events.size()).isEqualTo(2);
//        for (EventListDto dto : events) {
//            System.out.println(dto.getId() + " | " + dto.getName() + " | " + dto.getStartDate() + "~" + dto.getEndDate());
//        }
//    }
//
//    @Test
//    @DisplayName("셀럽의 예정 이벤트 목록")
//    void upcomingEventByCeleb() {
//        Long searchCelebId = 2l;
//
//        QEvent e = new QEvent("e");
//
//        List<EventListDto> events = queryFactory
//                .select(new QEventListDto(
//                        e.id,
//                        e.name,
//                        e.startDate,
//                        e.endDate,
//                        e.address,
//                        e.mainImageUrl
//                ))
//                .from(e)
//                .where(
//                        e.celebId.eq(searchCelebId)
//                            .and(Expressions.currentDate().lt(e.startDate))
//                )
//                .offset(0)  // 시작 인덱스
//                .limit(10)   // 개수
//                .orderBy(e.startDate.asc())
//                .fetch();
//
//        for (EventListDto dto : events) {
//            System.out.println(dto.getId() + " | " + dto.getName() + " | " + dto.getStartDate() + "~" + dto.getEndDate());
//        }
//    }
//
//    @Test
//    @DisplayName("셀럽의 지난 이벤트 목록")
//    void pastEventByCeleb() {
//        Long searchCelebId = 2l;
//
//        QEvent e = new QEvent("e");
//
//        List<EventListDto> events = queryFactory
//                .select(new QEventListDto(
//                        e.id,
//                        e.name,
//                        e.startDate,
//                        e.endDate,
//                        e.address,
//                        e.mainImageUrl
//                ))
//                .from(e)
//                .where(
//                        e.celebId.eq(searchCelebId)
//                                .and(Expressions.currentDate().gt(e.endDate))
//                )
//                .offset(0)  // 시작 인덱스
//                .limit(10)   // 개수
//                .orderBy(e.endDate.asc())
//                .fetch();
//
//        for (EventListDto dto : events) {
//            System.out.println(dto.getId() + " | " + dto.getName() + " | " + dto.getStartDate() + "~" + dto.getEndDate());
//        }
//    }
}
