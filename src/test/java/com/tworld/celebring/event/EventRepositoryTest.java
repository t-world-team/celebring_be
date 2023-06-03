package com.tworld.celebring.event;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tworld.celebring.event.model.Event;
import com.tworld.celebring.event.model.QEvent;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class EventRepositoryTest {

    @Autowired JPAQueryFactory queryFactory;

    @Test
    void startQueryDsl() {
        QEvent e = new QEvent("e");

        List<Event> events = queryFactory
                .select(e)
                .from(e)
                .where(e.celebId.eq((long) 2))
                .fetch();

        assertThat(events.size()).isEqualTo(3);
    }
}
