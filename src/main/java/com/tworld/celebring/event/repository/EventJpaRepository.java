package com.tworld.celebring.event.repository;

import com.tworld.celebring.event.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventJpaRepository extends JpaRepository<Event, Long> {
}
