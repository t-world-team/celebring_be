package com.tworld.celebring.event.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tworld.celebring.celeb.model.QViewCeleb;
import com.tworld.celebring.celeb.model.ViewCeleb;
import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.dto.QEventListDto;
import com.tworld.celebring.event.model.QEvent;
import com.tworld.celebring.event.model.QEventCeleb;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final JPAQueryFactory queryFactory;

    QEvent e = new QEvent("e");
    QEventCeleb ec = new QEventCeleb("ec");
    QViewCeleb vw = new QViewCeleb("vw");

    public List<EventListDto> findCurrentEvents(Pageable pageable) {
        return queryFactory
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
                .offset(pageable.getOffset())    // 시작 인덱스
                .limit(pageable.getPageSize())   // 개수
                .orderBy(e.startDate.asc())
                .fetch();
    }

    public long findCurrentEventsCount() {
        return queryFactory
                .selectFrom(e)
                .where(Expressions.currentDate().between(e.startDate, e.endDate))
                .fetch().size();
    }

    public long findEventsCountByCeleb(Long celebId) {
        return queryFactory
                .selectFrom(e)
                .join(ec).on(e.id.eq(ec.eventId).and(ec.celebId.eq(celebId)))
                .fetch().size();
    }

    public List<EventListDto> findCurrentEventsByCeleb(Long celebId) {
        return queryFactory
                .select(new QEventListDto(
                        e.id,
                        e.name,
                        e.startDate,
                        e.endDate,
                        e.cafeName,
                        e.address
                ))
                .from(e)
                .join(ec).on(e.id.eq(ec.eventId).and(ec.celebId.eq(celebId)))
                .where(Expressions.currentDate().between(e.startDate, e.endDate))
                .orderBy(e.startDate.asc())
                .fetch();
    }

    public List<EventListDto> findUpcomingEventsByCeleb(Long celebId) {
        return queryFactory
                .select(new QEventListDto(
                        e.id,
                        e.name,
                        e.startDate,
                        e.endDate,
                        e.cafeName,
                        e.address
                ))
                .from(e)
                .join(ec).on(e.id.eq(ec.eventId).and(ec.celebId.eq(celebId)))
                .where(Expressions.currentDate().lt(e.startDate))
                .orderBy(e.startDate.asc())
                .fetch();
    }

    public List<EventListDto> findPastEventsByCeleb(Long celebId) {
        return queryFactory
                .select(new QEventListDto(
                        e.id,
                        e.name,
                        e.startDate,
                        e.endDate,
                        e.cafeName,
                        e.address
                ))
                .from(e)
                .join(ec).on(e.id.eq(ec.eventId).and(ec.celebId.eq(celebId)))
                .where(Expressions.currentDate().gt(e.endDate))
                .orderBy(e.endDate.asc())
                .fetch();
    }

    public List<ViewCeleb> findCelebInfoByEvent(Long eventId) {
        return queryFactory
                .select(vw)
                .from(vw)
                .where(vw.id.in(
                        JPAExpressions
                                .select(ec.celebId)
                                .from(ec)
                                .where(ec.eventId.eq(eventId))
                ))
                .fetch();
    }
}
