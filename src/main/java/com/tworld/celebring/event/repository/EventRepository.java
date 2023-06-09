package com.tworld.celebring.event.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tworld.celebring.celeb.model.QViewCeleb;
import com.tworld.celebring.celeb.model.ViewCeleb;
import com.tworld.celebring.event.dto.*;
import com.tworld.celebring.event.model.QEvent;
import com.tworld.celebring.event.model.QEventCeleb;
import com.tworld.celebring.event.model.QEventLike;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    QEvent e = new QEvent("e");
    QEventCeleb ec = new QEventCeleb("ec");
    QEventLike el = new QEventLike("el");
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
                .where(Expressions.currentDate().between(e.startDate, e.endDate)
                        .and(e.deleteEntity.deleteYn.eq("N")))
                .offset(pageable.getOffset())    // 시작 인덱스
                .limit(pageable.getPageSize())   // 개수
                .orderBy(e.startDate.asc())
                .fetch();
    }

    public long findCurrentEventsCount() {
        return queryFactory
                .selectFrom(e)
                .where(Expressions.currentDate().between(e.startDate, e.endDate)
                        .and(e.deleteEntity.deleteYn.eq("N")))
                .fetch().size();
    }

    public long findEventsCountByCeleb(Long celebId) {
        return queryFactory
                .selectFrom(e)
                .join(ec).on(e.id.eq(ec.id.eventId).and(ec.id.celebId.eq(celebId))
                        .and(e.deleteEntity.deleteYn.eq("N")))
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
                .join(ec).on(e.id.eq(ec.id.eventId).and(ec.id.celebId.eq(celebId)))
                .where(Expressions.currentDate().between(e.startDate, e.endDate)
                        .and(e.deleteEntity.deleteYn.eq("N")))
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
                .join(ec).on(e.id.eq(ec.id.eventId).and(ec.id.celebId.eq(celebId)))
                .where(Expressions.currentDate().lt(e.startDate)
                        .and(e.deleteEntity.deleteYn.eq("N")))
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
                .join(ec).on(e.id.eq(ec.id.eventId).and(ec.id.celebId.eq(celebId)))
                .where(Expressions.currentDate().gt(e.endDate)
                        .and(e.deleteEntity.deleteYn.eq("N")))
                .orderBy(e.startDate.desc(), e.endDate.desc())
                .fetch();
    }

    public List<ViewCeleb> findCelebInfoByEvent(Long eventId) {
        return queryFactory
                .select(vw)
                .from(vw)
                .where(vw.id.in(
                        JPAExpressions
                                .select(ec.id.celebId)
                                .from(ec)
                                .where(ec.id.eventId.eq(eventId))
                ))
                .fetch();
    }

    public EventDetailDto findEventDetail(Long eventId, Long userId) {
        return queryFactory
                .select(new QEventDetailDto(
                        e.id,
                        e.name,
                        e.startDate,
                        e.endDate,
                        e.cafeName,
                        e.address,
                        e.mapX,
                        e.mapY,
                        e.openingTime,
                        e.sns,
                        (JPAExpressions.selectOne()
                                .from(el)
                                .where(el.id.userId.eq(userId)
                                        .and(el.id.eventId.eq(e.id)))),
                        (JPAExpressions.selectOne()
                                .from(e)
                                .where(e.createEntity.createBy.eq(userId)
                                        .and(e.id.eq(eventId))))
                ))
                .from(e)
                .where(e.id.eq(eventId))
                .fetchOne();
    }


    public Long deleteEvent(Long eventId, Long userId) {
        return queryFactory
                .update(e)
                .set(e.deleteEntity.deleteYn, "Y")
                .set(e.deleteEntity.deleteBy, userId)
                .set(e.deleteEntity.deleteAt, Expressions.currentDate())
                .where(e.id.eq(eventId))
                .execute();
    }

    public List<EventListDto> findMyEventList(Long userId, Pageable pageable) {
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
                .where(e.createEntity.createBy.eq(userId)
                        .and(e.deleteEntity.deleteYn.eq("N")))
                .offset(pageable.getOffset())    // 시작 인덱스
                .limit(pageable.getPageSize())   // 개수
                .orderBy(e.id.desc())
                .fetch();
    }

    public long findMyEventsCount(Long userId) {
        return queryFactory
                .selectFrom(e)
                .where(e.createEntity.createBy.eq(userId)
                        .and(e.deleteEntity.deleteYn.eq("N")))
                .fetch().size();
    }

    public List<EventListDto> findFavoriteEventList(Long userId, Pageable pageable) {
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
                .where(e.id.in(
                                JPAExpressions.select(el.id.eventId)
                                        .from(el)
                                        .where(el.id.userId.eq(userId))
                        )
                        .and(e.deleteEntity.deleteYn.eq("N")))
                .offset(pageable.getOffset())    // 시작 인덱스
                .limit(pageable.getPageSize())   // 개수
                .orderBy(e.id.desc())
                .fetch();
    }

    public List<EventListDto> findEventListByCelebAndDay(Long celebId, String day, Pageable pageable) {
        BooleanExpression template = Expressions.booleanTemplate(
                "STR_TO_DATE({0}, '%Y-%m-%d') between {1} and {2}",
                day,
                e.startDate,
                e.endDate
        );

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
                .join(ec).on(e.id.eq(ec.id.eventId).and(ec.id.celebId.eq(celebId)))
                .where(template
                        .and(e.deleteEntity.deleteYn.eq("N")))
                .offset(pageable.getOffset())    // 시작 인덱스
                .limit(pageable.getPageSize())   // 개수
                .orderBy(e.id.desc())
                .fetch();
    }

    public List<EventListDto> findEventByCeleb(Long celebId) {
        return queryFactory
                .select(new QEventListDto(
                        e.id,
                        e.startDate,
                        e.endDate
                ))
                .from(e)
                .join(ec).on(e.id.eq(ec.id.eventId).and(ec.id.celebId.eq(celebId)))
                .where(e.deleteEntity.deleteYn.eq("N"))
                .fetch();
    }

    public List<String> findEventDays(EventListDto dto) {
        String nativeQuery = """
                WITH RECURSIVE CTE  AS (
                    SELECT DATE_FORMAT( :startdate, '%Y-%m-%d') AS DT FROM DUAL
                    UNION ALL
                    SELECT DATE_ADD(DT, INTERVAL 1 DAY) FROM CTE
                    WHERE DT < DATE_FORMAT( :enddate, '%Y-%m-%d')
                )
                SELECT date_format(DT,'%Y-%m-%d') AS TDATE
                FROM CTE""";

        return em.createNativeQuery(nativeQuery)
                .setParameter("startdate", dto.getStartDate())
                .setParameter("enddate", dto.getEndDate())
                .getResultList();
    }
}
