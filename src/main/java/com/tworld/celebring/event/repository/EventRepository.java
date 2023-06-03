package com.tworld.celebring.event.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tworld.celebring.event.dto.EventListDto;
import com.tworld.celebring.event.dto.QEventListDto;
import com.tworld.celebring.event.model.QEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepository {
    private final JPAQueryFactory queryFactory;

    QEvent e = new QEvent("e");

    public List<EventListDto> findNowEvents() {
        return queryFactory
                .select(new QEventListDto(
                        e.id,
                        e.name,
                        e.startDate,
                        e.endDate,
                        e.address,
                        e.mainImageUrl
                ))
                .from(e)
                .where(Expressions.currentDate().between(e.startDate, e.endDate))
                .fetch();
    }
}
